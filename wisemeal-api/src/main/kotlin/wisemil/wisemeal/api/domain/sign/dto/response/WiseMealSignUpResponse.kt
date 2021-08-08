package wisemil.wisemeal.api.domain.sign.dto.response

import wisemil.wisemeal.application.domain.member.dto.WisemealMemberDto
import wisemil.wisemeal.core.domain.member.model.MemberStatus

data class WiseMealSignUpResponse(
    val memberNumber: String,
    val status: MemberStatus,
) {
    companion object {
        fun from(wisemealMemberDto: WisemealMemberDto): WiseMealSignUpResponse {
            return WiseMealSignUpResponse(
                memberNumber = wisemealMemberDto.memberNumber,
                status = wisemealMemberDto.status!!,
            )
        }
    }
}
