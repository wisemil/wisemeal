package wisemil.wisemeal.api.domain.security.oauth.component

import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import wisemil.wisemeal.api.domain.security.oauth.http.OAuthHttpResponse
import wisemil.wisemeal.api.domain.security.wisemeal.component.WisemealAuthenticationSuccessHandler
import wisemil.wisemeal.api.domain.security.wisemeal.dto.SessionMember.Companion.SESSION_KEY
import wisemil.wisemeal.common.log.logger
import wisemil.wisemeal.common.type.web.WiseMealHttpHeader
import wisemil.wisemeal.jwt.spec.JwtToken
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class OAuthAuthenticationSuccessHandler(
    private val httpCookieOAuth2AuthorizationRequestRepository: HttpCookieOAuth2AuthorizationRequestRepository,
    private val jwtToken: JwtToken,
) : WisemealAuthenticationSuccessHandler() {
    private val log = logger()

    override fun responseAfterAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication?,
    ) {
        val targetUrl = determineTargetUrl(request, response, authentication!!)
        if (response.isCommitted) {
            log.debug("Response has already been committed. Unable to redirect to $targetUrl")
            return
        }

        clearAuthentication(request, response)

        val oAuthToken = authentication as OAuth2AuthenticationToken
        val memberNumber = oAuthToken.principal.attributes[SESSION_KEY] as String

        val oauthResponse = OAuthHttpResponse(response)

        val jwt = jwtToken.create(memberNumber)
        oauthResponse.addBearer(jwt)
        oauthResponse.addCookie(WiseMealHttpHeader.JWT.header, jwt)

        redirectStrategy.sendRedirect(request, oauthResponse.delegate, targetUrl)
    }

    override fun clearAuthentication(request: HttpServletRequest, response: HttpServletResponse) {
        super.clearAuthenticationAttributes(request)
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationCookies(request, response)
        request.session.invalidate()
    }
}
