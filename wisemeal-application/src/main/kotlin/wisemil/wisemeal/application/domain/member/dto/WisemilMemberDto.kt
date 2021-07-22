package wisemil.wisemeal.application.domain.member.dto

import wisemil.wisemeal.core.domain.member.entity.WisemilMember
import wisemil.wisemeal.core.domain.member.model.MemberRole
import wisemil.wisemeal.core.domain.member.model.SignType

data class WisemilMemberDto(
    val id: Long?,
    val email: String,
    val memberNumber: String,
    val role: MemberRole,
    val nickname: String? = null,

    val password: String? = null,
    val signType: SignType? = null,
) {
    companion object {
        fun fromFetch(member: WisemilMember): WisemilMemberDto {
            return WisemilMemberDto(
                id = member.id,
                email = member.email,
                memberNumber = member.memberNumber,
                role = member.role,
                password = member.memberDetail.encryptedPassword,
                signType = member.signType,
                nickname = member.memberDetail.nickname
            )
        }

        fun from(member: WisemilMember): WisemilMemberDto {
            return WisemilMemberDto(
                id = member.id,
                email = member.email,
                memberNumber = member.memberNumber,
                signType = member.signType,
                role = member.role,
            )
        }
    }
}