package wisemil.wisemeal.jwt.impl.oauth0

import com.auth0.jwt.algorithms.Algorithm
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class OAuth0JwtTokenTest {
    private val algoHMAC256 = Algorithm.HMAC256("secret length should over the sixteen byte")

    @Test
    fun `jwt encode and decode`() {
        //given
        val token = OAuth0JwtToken(algoHMAC256)
        val serializedToken = token.create("1234")

        //when
        val payloads = token.decode(serializedToken)

        //then
        assertThat(payloads.get("memberNumber", String::class.java)).isEqualTo("1234")
    }

}
