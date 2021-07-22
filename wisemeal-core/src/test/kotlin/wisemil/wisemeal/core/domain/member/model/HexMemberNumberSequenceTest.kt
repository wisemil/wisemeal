package wisemil.wisemeal.core.domain.member.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class HexMemberNumberSequenceTest {
    @Test
    fun `hex 변환시 6자리가 되지 않는 작은 수는 사용할 수 없다`() {
        assertThrows<IllegalArgumentException> { HexMemberNumberSequence(HexMemberNumberSequence.MIN - 1) }
            .run { assertThat(this.message).contains("invalid sequence:${HexMemberNumberSequence.MIN - 1}") }
    }

    @Test
    fun `hex 변환시 6자리가 되지 않는 큰 수는 사용할 수 없다`() {
        assertThrows<IllegalArgumentException> { HexMemberNumberSequence(HexMemberNumberSequence.MAX + 1) }
            .run { assertThat(this.message).contains("invalid sequence:${HexMemberNumberSequence.MAX + 1}") }
    }

    @Test
    fun `hex 변환으로 6자리 숫자를 생성한다`() {
        //given
        val hexMemberNumberSequence = HexMemberNumberSequence(HexMemberNumberSequence.MID)

        //when
        val hax = hexMemberNumberSequence.hexSequence

        //then
        assertThat(hax.length).isEqualTo(6)
    }

    @Test
    fun `다음 시퀀스 값을 반환한다`() {
        //given
        val hexMemberNumberSequence = HexMemberNumberSequence(HexMemberNumberSequence.MID)

        //when
        val next = hexMemberNumberSequence.nextSequence

        //then
        assertThat(next).isEqualTo(HexMemberNumberSequence.MID + 1)
    }

    @Test
    fun `hex 최대치에 다다르면 다음값은 6자리가 될수 있는 최저값이다`() {
        //given
        val hexMemberNumberSequence = HexMemberNumberSequence(HexMemberNumberSequence.MAX)

        //when
        val min = hexMemberNumberSequence.nextSequence

        //then
        assertThat(min).isEqualTo(HexMemberNumberSequence.MIN)
    }
}
