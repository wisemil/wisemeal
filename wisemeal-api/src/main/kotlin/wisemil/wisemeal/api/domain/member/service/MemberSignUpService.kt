package wisemil.wisemeal.api.domain.member.service

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import wisemil.wisemeal.application.domain.member.dto.WisemilMemberDto
import wisemil.wisemeal.application.domain.member.repository.WiseMilMemberRepository
import wisemil.wisemeal.core.domain.member.dto.WisemilMemberCreator
import java.time.LocalDate

@Service
@Transactional
class MemberSignUpService(
    private val memberRepository: WiseMilMemberRepository,
    private val memberNumberGenerator: MemberNumberGenerator,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
) {
    fun signUp(wisemilMemberCreator: WisemilMemberCreator, registerDate: LocalDate = LocalDate.now()): WisemilMemberDto {
        //check
        memberRepository.findByEmailAndSignType(wisemilMemberCreator.email, wisemilMemberCreator.signType)
            ?.run { throw IllegalArgumentException("duplicated email:$email, signType:$signType") }

        //create
        val memberNumber = memberNumberGenerator.generate(registerDate)
        val member = wisemilMemberCreator.toWisemilMember(
            encryptedPasswordProvider = { bCryptPasswordEncoder.encode(it) },
            memberNumber = memberNumber,
        )

        //save
        val savedMember = memberRepository.save(member)

        return WisemilMemberDto.fromFetch(savedMember)
    }
}
