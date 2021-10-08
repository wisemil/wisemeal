package wisemil.wisemeal.api.domain.security.wisemeal.dto

import org.springframework.security.core.GrantedAuthority
import wisemil.wisemeal.api.domain.security.wisemeal.dto.SessionMember.Companion.SESSION_KEY
import wisemil.wisemeal.core.domain.member.model.MemberRole
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession

class WiseMealSession(
    private val session: HttpSession,
) {
    fun sessionMember(memberNumber: String, authorities: Collection<GrantedAuthority>) {
        val roles = authorities.map { MemberRole.valueOf(it.authority) }
        sessionMember(memberNumber, roles.toSet())
    }

    fun sessionMember(memberNumber: String, roles: Set<MemberRole>) {
        session.setAttribute(SESSION_KEY, SessionMember(memberNumber, roles))
    }

    fun sessionMember(): SessionMember? {
        return session.getAttribute(SESSION_KEY) as SessionMember?
    }

    companion object {
        fun from(request: HttpServletRequest): WiseMealSession {
            return WiseMealSession(request.session)
        }
    }
}
