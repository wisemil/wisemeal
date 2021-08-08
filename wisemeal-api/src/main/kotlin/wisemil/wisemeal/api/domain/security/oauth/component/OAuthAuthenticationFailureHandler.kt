package wisemil.wisemeal.api.domain.security.oauth.component

import org.springframework.security.core.AuthenticationException
import wisemil.wisemeal.api.domain.security.wisemeal.component.WisemealAuthenticationFailureHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class OAuthAuthenticationFailureHandler(
    private val httpCookieOAuth2AuthorizationRequestRepository: HttpCookieOAuth2AuthorizationRequestRepository,
) : WisemealAuthenticationFailureHandler() {
    override fun removeAuthorizationCookie(request: HttpServletRequest, response: HttpServletResponse, exception: AuthenticationException) {
        super.removeAuthorizationCookie(request, response, exception)
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationCookies(request, response)
    }
}
