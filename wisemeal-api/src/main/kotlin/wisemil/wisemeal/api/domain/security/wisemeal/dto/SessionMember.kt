package wisemil.wisemeal.api.domain.security.wisemeal.dto

import wisemil.wisemeal.core.domain.member.model.MemberRole
import java.io.Serializable

data class SessionMember(
    val memberNumber: String,
    val roles: Set<MemberRole>,
) : Serializable {
    companion object {
        const val SESSION_KEY = "WISEMEAL_MEMBER"
    }
}
