package wisemil.wisemeal.api.domain.security.oauth.component

import org.springframework.security.core.Authentication
import wisemil.wisemeal.api.domain.security.wisemeal.component.WisemealAuthenticationSuccessHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class OAuthAuthenticationSuccessHandler(
    private val httpCookieOAuth2AuthorizationRequestRepository: HttpCookieOAuth2AuthorizationRequestRepository,
) : WisemealAuthenticationSuccessHandler() {

    override fun responseAfterAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication?,
    ) {
        val targetUrl = determineTargetUrl(request, response, authentication!!)
        if (response.isCommitted) {
            logger.debug("Response has already been committed. Unable to redirect to $targetUrl")
            return
        }
        clearAuthenticationAttributes(request, response)
        redirectStrategy.sendRedirect(request, response, targetUrl)
    }

    override fun clearAuthenticationAttributes(request: HttpServletRequest, response: HttpServletResponse) {
        super.clearAuthenticationAttributes(request)
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationCookies(request, response)
    }
}
