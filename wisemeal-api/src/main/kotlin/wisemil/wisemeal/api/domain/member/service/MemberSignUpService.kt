package wisemil.wisemeal.api.domain.member.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import wisemil.wisemeal.application.domain.member.dto.WisemilMemberDto
import wisemil.wisemeal.application.domain.member.repository.WiseMilMemberRepository
import wisemil.wisemeal.core.domain.member.entity.MemberDetail
import wisemil.wisemeal.core.domain.member.entity.WisemilMember
import wisemil.wisemeal.core.domain.member.model.MemberRole
import wisemil.wisemeal.core.domain.member.model.SignType
import java.time.LocalDate

@Service
@Transactional
class MemberSignUpService(
    private val memberRepository: WiseMilMemberRepository,
    private val memberNumberGenerator: MemberNumberGenerator,
) {
    fun signUp(email: String, signType: SignType, password: String? = null, date: LocalDate = LocalDate.now()): WisemilMemberDto {
        //check
        memberRepository.findByEmailAndSignType(email,signType)
            ?.run { throw IllegalArgumentException("duplicated email:$email, signType:$signType") }

        //create
        val member = WisemilMember(
            email = email,
            memberNumber = memberNumberGenerator.generate(date),
            memberDetail = MemberDetail(encryptedPassword = password),
            signType = signType,
            role = MemberRole.MEMBER,
        )

        //save
        val savedMember = memberRepository.save(member)

        return WisemilMemberDto.fromFetch(savedMember)
    }
}
