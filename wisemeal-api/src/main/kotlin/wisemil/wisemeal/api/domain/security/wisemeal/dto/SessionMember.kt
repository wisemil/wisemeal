package wisemil.wisemeal.api.domain.security.wisemeal.dto

import wisemil.wisemeal.application.domain.member.dto.WisemealMemberDto
import java.io.Serializable

data class SessionMember(
    val memberNumber: String,
) : Serializable {
    companion object {
        const val SESSION_KEY = "MEMBER"
        fun from(member: WisemealMemberDto): SessionMember {
            return SessionMember(
                memberNumber = member.memberNumber,
            )
        }
    }
}
