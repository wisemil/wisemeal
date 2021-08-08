package wisemil.wisemeal.api.domain.security.wisemeal.component

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.web.util.UriComponentsBuilder
import wisemil.wisemeal.api.domain.security.extension.cookie
import wisemil.wisemeal.api.domain.security.oauth.component.HttpCookieOAuth2AuthorizationRequestRepository
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

open class WisemealAuthenticationFailureHandler : SimpleUrlAuthenticationFailureHandler() {

    override fun onAuthenticationFailure(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: AuthenticationException,
    ) {
        var targetUrl: String = request.cookie(HttpCookieOAuth2AuthorizationRequestRepository.WISEMIL_REDIRECT_URI)
            ?.value
            ?: "/"

        targetUrl = UriComponentsBuilder.fromUriString(targetUrl)
            .queryParam("error", exception.localizedMessage)
            .build().toUriString()

        removeAuthorizationCookie(request, response, exception)

        redirectStrategy.sendRedirect(request, response, targetUrl)
    }

    protected open fun removeAuthorizationCookie(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: AuthenticationException,
    ) {
    }
}
