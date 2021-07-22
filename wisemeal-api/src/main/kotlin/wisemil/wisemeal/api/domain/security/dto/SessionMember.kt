package wisemil.wisemeal.api.domain.security.dto

import wisemil.wisemeal.application.domain.member.dto.WisemilMemberDto
import java.io.Serializable

data class SessionMember(
    val memberNumber: String,
) : Serializable {
    companion object {
        const val SESSION_KEY = "MEMBER"
        fun from(member: WisemilMemberDto): SessionMember {
            return SessionMember(
                memberNumber = member.memberNumber,
            )
        }
    }
}
