package wisemil.wisemeal.api.domain.member.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import wisemil.wisemeal.api.test.ApiSpringTest
import wisemil.wisemeal.application.domain.member.repository.WiseMilMemberRepository
import wisemil.wisemeal.core.domain.member.dto.WisemilMemberCreator
import wisemil.wisemeal.core.domain.member.model.SignType

@ApiSpringTest
internal class MemberSignUpServiceTest @Autowired constructor(
    private val memberSignUpService: MemberSignUpService,
    private val memberRepository: WiseMilMemberRepository,
) {

    @AfterEach
    internal fun tearDown() {
        memberRepository.deleteAll()
    }

    @Test
    fun `signUp - 회원가입 성공`() {
        //given
        val creator = WisemilMemberCreator(
            email = "pci2676@gmail.com",
            signType = SignType.WISEMIL,
            nickName = null,
            password = "abc",
        )

        //when
        val dto = memberSignUpService.signUp(creator)

        //then
        val member = memberRepository.findByMemberNumberFetch(memberNumber = dto.memberNumber)
        assertThat(member).isNotNull
    }

    @Test
    fun `signUp - 회원가입 실패 signType + email = uk`() {
        //given
        val creator1 = WisemilMemberCreator(
            email = "pci2676@gmail.com",
            signType = SignType.WISEMIL,
            nickName = null,
            password = "abc",
        )
        memberSignUpService.signUp(creator1)

        val creator2 = WisemilMemberCreator(
            email = "pci2676@gmail.com",
            signType = SignType.WISEMIL,
            nickName = null,
            password = "abc",
        )

        //when
        //then
        assertThrows<IllegalArgumentException> {
            memberSignUpService.signUp(creator2)
        }.run { assertThat(this.message).contains("duplicated email:${creator2.email}, signType:${creator2.signType}") }
    }
}
