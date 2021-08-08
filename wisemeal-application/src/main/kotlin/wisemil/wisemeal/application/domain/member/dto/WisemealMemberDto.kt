package wisemil.wisemeal.application.domain.member.dto

import wisemil.wisemeal.core.domain.member.entity.WisemealMember
import wisemil.wisemeal.core.domain.member.model.MemberRole
import wisemil.wisemeal.core.domain.member.model.MemberStatus
import wisemil.wisemeal.core.domain.member.model.SignType

data class WisemealMemberDto(
    val id: Long?,
    val memberNumber: String,
    val email: String,
    val signType: SignType? = null,
    val role: MemberRole,

    val status: MemberStatus? = null,
    val nickname: String? = null,
    val password: String? = null,
) {
    companion object {
        fun fromFetch(member: WisemealMember): WisemealMemberDto {
            return WisemealMemberDto(
                id = member.id,
                email = member.email,
                memberNumber = member.memberNumber,
                role = member.role,
                password = member.memberDetail.encryptedPassword,
                signType = member.signType,
                nickname = member.memberDetail.nickname,
                status = member.memberDetail.status,
            )
        }

        fun from(member: WisemealMember): WisemealMemberDto {
            return WisemealMemberDto(
                id = member.id,
                email = member.email,
                memberNumber = member.memberNumber,
                signType = member.signType,
                role = member.role,
            )
        }
    }
}
