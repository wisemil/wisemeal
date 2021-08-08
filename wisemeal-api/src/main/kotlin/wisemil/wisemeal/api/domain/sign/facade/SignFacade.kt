package wisemil.wisemeal.api.domain.sign.facade

import org.springframework.stereotype.Component
import wisemil.wisemeal.api.domain.sign.dto.request.WiseMealSignInRequest
import wisemil.wisemeal.api.domain.sign.dto.request.WiseMealSignUpRequest
import wisemil.wisemeal.api.domain.sign.service.SignService
import wisemil.wisemeal.application.domain.member.dto.WisemealMemberDto

@Component
class SignFacade(
    private val signService: SignService,
) {
    fun signUpWisemeal(request: WiseMealSignUpRequest): WisemealMemberDto {
        val creator = request.toWiseMealMemberCreator()

        return signService.signUp(creator)
    }

    fun signInWiseMeal(request: WiseMealSignInRequest): WisemealMemberDto {
        val email = request.email
        val password = request.password

        return signService.signIn(email = email, rawPassword = password)
    }
}
