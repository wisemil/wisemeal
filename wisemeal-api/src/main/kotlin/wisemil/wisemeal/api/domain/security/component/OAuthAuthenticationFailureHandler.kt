package wisemil.wisemeal.api.domain.security.component

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder
import wisemil.wisemeal.api.domain.security.component.HttpCookieOAuth2AuthorizationRequestRepository.Companion.WISEMIL_REDIRECT_URI
import wisemil.wisemeal.api.domain.security.extension.cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class OAuthAuthenticationFailureHandler(
    private val httpCookieOAuth2AuthorizationRequestRepository: HttpCookieOAuth2AuthorizationRequestRepository,
) : SimpleUrlAuthenticationFailureHandler() {

    override fun onAuthenticationFailure(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: AuthenticationException,
    ) {
        var targetUrl: String = request.cookie(WISEMIL_REDIRECT_URI)
            ?.value
            ?: "/"

        targetUrl = UriComponentsBuilder.fromUriString(targetUrl)
            .queryParam("error", exception.localizedMessage)
            .build().toUriString()

        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationCookies(request, response)

        redirectStrategy.sendRedirect(request, response, targetUrl)
    }
}
