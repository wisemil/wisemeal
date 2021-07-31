package wisemil.wisemeal.api.domain.member.dto

import wisemil.wisemeal.application.domain.member.dto.WisemilMemberDto
import wisemil.wisemeal.core.domain.member.model.MemberStatus

data class WiseMilMemberCreateResponse(
    val memberNumber: String,
    val status: MemberStatus,
) {
    companion object {
        fun from(wisemilMemberDto: WisemilMemberDto): WiseMilMemberCreateResponse {
            return WiseMilMemberCreateResponse(
                memberNumber = wisemilMemberDto.memberNumber,
                status = wisemilMemberDto.status,
            )
        }
    }
}
