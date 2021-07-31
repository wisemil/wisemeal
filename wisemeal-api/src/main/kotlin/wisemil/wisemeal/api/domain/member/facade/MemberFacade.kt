package wisemil.wisemeal.api.domain.member.facade

import org.springframework.stereotype.Component
import wisemil.wisemeal.api.domain.member.dto.WiseMilMemberCreateRequest
import wisemil.wisemeal.api.domain.member.service.MemberSignUpService
import wisemil.wisemeal.application.domain.member.dto.WisemilMemberDto

@Component
class MemberFacade(
    private val memberSignUpService: MemberSignUpService,
) {
    fun signUpWisemil(request: WiseMilMemberCreateRequest): WisemilMemberDto {
        val creator = request.toWiseMilMemberCreator()

        return memberSignUpService.signUp(creator)
    }
}
