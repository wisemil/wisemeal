package wisemil.wisemeal.api.domain.sign.service

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import wisemil.wisemeal.application.domain.member.dto.WisemealMemberDto
import wisemil.wisemeal.application.domain.member.repository.WiseMealMemberRepository
import wisemil.wisemeal.application.domain.member.service.MemberNumberGenerator
import wisemil.wisemeal.common.exception.base.WisemealBadRequestException
import wisemil.wisemeal.common.exception.member.MemberNotFoundException
import wisemil.wisemeal.common.response.ApiResponseCode
import wisemil.wisemeal.core.domain.member.dto.WisemealMemberCreator
import wisemil.wisemeal.core.domain.member.model.Password
import wisemil.wisemeal.core.domain.member.model.SignType
import java.time.LocalDate
import java.time.LocalDateTime

@Service
@Transactional
class SignService(
    private val memberRepository: WiseMealMemberRepository,
    private val memberNumberGenerator: MemberNumberGenerator,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
) {
    fun signUp(wisemealMemberCreator: WisemealMemberCreator, registerDate: LocalDate = LocalDate.now()): WisemealMemberDto {
        memberRepository.findByEmailAndSignType(wisemealMemberCreator.email, wisemealMemberCreator.signType)
            ?.run {
                throw WisemealBadRequestException(
                    apiResponseCode = ApiResponseCode.ALREADY_SIGN_UP,
                    logMessage = "duplicated email:$email, signType:$signType"
                )
            }

        //create
        val memberNumber = memberNumberGenerator.generate(registerDate)
        val member = wisemealMemberCreator.toWisemilMember(
            encryptedPasswordProvider = { bCryptPasswordEncoder.encode(it) },
            memberNumber = memberNumber,
        )

        val savedMember = memberRepository.save(member)

        return WisemealMemberDto.fromFetch(savedMember)
    }

    fun signIn(email: String, rawPassword: String, attemptTime: LocalDateTime = LocalDateTime.now()): WisemealMemberDto {
        val member = memberRepository.findByEmailAndSignTypeFetch(email = email, signType = SignType.WISEMEAL)
            ?: throw MemberNotFoundException()
        val memberDetail = member.memberDetail

        val password = Password(rawPassword, bCryptPasswordEncoder::matches)
        memberDetail.attemptLogin(password, attemptTime)

        return WisemealMemberDto.fromFetch(member)
    }

}
