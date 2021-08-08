package wisemil.wisemeal.api.domain.sign.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import wisemil.wisemeal.api.domain.sign.dto.request.WiseMealSignInRequest
import wisemil.wisemeal.api.domain.sign.dto.request.WiseMealSignUpRequest
import wisemil.wisemeal.api.domain.sign.dto.response.WiseMealSignInResponse
import wisemil.wisemeal.api.domain.sign.dto.response.WiseMealSignUpResponse
import wisemil.wisemeal.api.domain.sign.facade.SignFacade
import wisemil.wisemeal.common.response.ApiResponse
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/members")
class SignController(
    private val signFacade: SignFacade,
) {

    /**
     * wisemeal 회원 가입
     */
    @PostMapping
    fun signUpWisemeal(@Valid @RequestBody request: WiseMealSignUpRequest): ApiResponse<WiseMealSignUpResponse> {
        val wisemilMemberDto = signFacade.signUpWisemeal(request)

        val response = WiseMealSignUpResponse.from(wisemilMemberDto)

        return ApiResponse.ok(response)
    }

    /**
     * wisemeal 로그인
     */
    @PostMapping("/login")
    fun signInWisemeal(@Valid @RequestBody request: WiseMealSignInRequest): ApiResponse<WiseMealSignInResponse> {
        val wisemealMemberDto = signFacade.signInWiseMeal(request)

        val response = WiseMealSignInResponse(wisemealMemberDto.memberNumber)

        return ApiResponse.ok(response)
    }
}
