package wisemil.wisemeal.core.domain.member.dto

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import wisemil.wisemeal.core.domain.member.model.SignType

internal class WisemilMemberCreatorTest {

    @Test
    fun `toWisemilMember - 비밀번호가 null 이면 비밀번호 없이 wisemil member 를 생성한다`() {
        //given
        val creator = WisemilMemberCreator(
            email = "pci2676@gmail.com",
            signType = SignType.WISEMIL,
            nickName = null,
            password = null
        )

        //when
        val member = creator.toWisemilMember(
            memberNumber = "memberNumber",
            encryptedPasswordProvider = { it.reversed() },
        )

        //then
        assertThat(member.memberDetail.encryptedPassword).isNull()
    }

    @Test
    fun `toWisemilMember - 비밀번호가 null 이 아니면 비밀번호와 함께 wisemil member 를 생성한다`() {
        //given
        val creator = WisemilMemberCreator(
            email = "pci2676@gmail.com",
            signType = SignType.WISEMIL,
            nickName = null,
            password = "password"
        )

        //when
        val member = creator.toWisemilMember(
            memberNumber = "memberNumber",
            encryptedPasswordProvider = { it.reversed() },
        )

        //then
        assertThat(member.memberDetail.encryptedPassword).isEqualTo("drowssap")
    }
}
