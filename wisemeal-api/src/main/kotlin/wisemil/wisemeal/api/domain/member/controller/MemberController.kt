package wisemil.wisemeal.api.domain.member.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import wisemil.wisemeal.api.domain.member.dto.WiseMilMemberCreateRequest
import wisemil.wisemeal.api.domain.member.dto.WiseMilMemberCreateResponse
import wisemil.wisemeal.api.domain.member.facade.MemberFacade
import wisemil.wisemeal.common.response.ApiResponse
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/members")
class MemberController(
    private val memberFacade: MemberFacade,
) {

    /**
     * wisemil 회원 가입
     */
    @PostMapping
    fun signUpWisemil(@Valid @RequestBody request: WiseMilMemberCreateRequest): ApiResponse<WiseMilMemberCreateResponse> {
        val wisemilMemberDto = memberFacade.signUpWisemil(request)

        val response = WiseMilMemberCreateResponse.from(wisemilMemberDto)

        return ApiResponse.ok(response)
    }

}
