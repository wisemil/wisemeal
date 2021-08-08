package wisemil.wisemeal.api.domain.sign.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import wisemil.wisemeal.api.test.ApiSpringTest
import wisemil.wisemeal.application.domain.member.repository.WiseMealMemberRepository
import wisemil.wisemeal.common.exception.base.WisemealBadRequestException
import wisemil.wisemeal.common.exception.member.IncorrectPasswordException
import wisemil.wisemeal.common.exception.member.MemberNotFoundException
import wisemil.wisemeal.common.response.ApiResponseCode
import wisemil.wisemeal.core.domain.member.dto.WisemealMemberCreator
import wisemil.wisemeal.core.domain.member.model.SignType

@ApiSpringTest
internal class SignServiceTest @Autowired constructor(
    private val signService: SignService,
    private val memberRepository: WiseMealMemberRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
) {

    @AfterEach
    internal fun tearDown() {
        memberRepository.deleteAll()
    }

    @Test
    fun `signUp - 회원가입 성공`() {
        //given
        val creator = WisemealMemberCreator(
            email = "pci2676@gmail.com",
            signType = SignType.WISEMEAL,
            nickName = null,
            rawPassword = "abc",
        )

        //when
        val dto = signService.signUp(creator)

        //then
        val member = memberRepository.findByMemberNumberFetch(memberNumber = dto.memberNumber)
        assertThat(member).isNotNull
    }

    @Test
    fun `signUp - 회원가입 실패 signType + email = uk`() {
        //given
        val creator1 = WisemealMemberCreator(
            email = "pci2676@gmail.com",
            signType = SignType.WISEMEAL,
            nickName = null,
            rawPassword = "abc",
        )
        signService.signUp(creator1)

        val creator2 = WisemealMemberCreator(
            email = "pci2676@gmail.com",
            signType = SignType.WISEMEAL,
            nickName = null,
            rawPassword = "abc",
        )

        //when
        //then
        assertThrows<WisemealBadRequestException> {
            signService.signUp(creator2)
        }.run { assertThat(this.message).contains(ApiResponseCode.ALREADY_SIGN_UP.message) }
    }

    @Test
    fun `로그인 성공`() {
        //given
        val creator = WisemealMemberCreator(
            email = "pci2676@gmail.com",
            signType = SignType.WISEMEAL,
            nickName = null,
            rawPassword = "password"
        )
        val member = creator.toWisemilMember("memberNumber") { bCryptPasswordEncoder.encode(it) }
        memberRepository.save(member)

        //when
        val wisemealMemberDto = signService.signIn(
            email = member.email,
            rawPassword = creator.rawPassword!!,
        )

        //then
        assertThat(wisemealMemberDto.email).isEqualTo(member.email)

    }

    @Test
    fun `로그인 실패 - 이메일 없음`() {
        //given
        val creator = WisemealMemberCreator(
            email = "pci2676@gmail.com",
            signType = SignType.WISEMEAL,
            nickName = null,
            rawPassword = "password"
        )
        val member = creator.toWisemilMember("memberNumber") { "encPassword" }
        memberRepository.save(member)

        //when
        assertThrows<MemberNotFoundException> {
            signService.signIn(
                email = "wrong@email.com",
                rawPassword = creator.rawPassword!!,
            )
        }
    }

    @Test
    fun `로그인 실패 - 이메일은 같지만 가입 유형이 다름`() {
        //given
        val creator = WisemealMemberCreator(
            email = "pci2676@gmail.com",
            signType = SignType.GOOGLE,
            nickName = null,
            rawPassword = null
        )
        val member = creator.toWisemilMember("memberNumber")
        memberRepository.save(member)

        //when
        assertThrows<MemberNotFoundException> {
            signService.signIn(
                email = member.email,
                rawPassword = "",
            )
        }
    }

    @Test
    fun `로그인 실패 - 비밀번호 다름`() {
        //given
        val creator = WisemealMemberCreator(
            email = "pci2676@gmail.com",
            signType = SignType.WISEMEAL,
            nickName = null,
            rawPassword = "password"
        )
        val member = creator.toWisemilMember("memberNumber") { "encPassword" }
        memberRepository.save(member)

        //when
        assertThrows<IncorrectPasswordException> {
            signService.signIn(
                email = member.email,
                rawPassword = "wrongPassword",
            )
        }
    }
}
