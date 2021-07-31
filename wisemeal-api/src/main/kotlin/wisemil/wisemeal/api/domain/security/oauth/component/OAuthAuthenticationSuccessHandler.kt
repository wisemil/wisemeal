package wisemil.wisemeal.api.domain.security.oauth.component

import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder
import wisemil.wisemeal.api.domain.security.oauth.component.HttpCookieOAuth2AuthorizationRequestRepository.Companion.WISEMIL_REDIRECT_URI
import wisemil.wisemeal.api.domain.security.oauth.extension.cookie
import wisemil.wisemeal.common.log.logger
import java.net.URI
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class OAuthAuthenticationSuccessHandler(
    private val httpCookieOAuth2AuthorizationRequestRepository: HttpCookieOAuth2AuthorizationRequestRepository,
) : SimpleUrlAuthenticationSuccessHandler() {
    private val log = logger()

    override fun onAuthenticationSuccess(
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

    override fun determineTargetUrl(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication,
    ): String {
        val redirectUri = request.cookie(WISEMIL_REDIRECT_URI)?.value
        log.info { "redirect_uri:${redirectUri}" }

        redirectUri?.run {
            checkAuthorizedRedirectUri(this)
        }

        val targetUrl: String = redirectUri ?: defaultTargetUrl
        return UriComponentsBuilder.fromUriString(targetUrl)
            .build().toUriString()
    }

    private fun checkAuthorizedRedirectUri(redirectUri: String) {
        val clientRedirectUri = URI.create(redirectUri)
        // TODO 등록된 redirect uri 만 허용하도록
    }

    protected fun clearAuthenticationAttributes(request: HttpServletRequest, response: HttpServletResponse) {
        super.clearAuthenticationAttributes(request)
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationCookies(request, response)
    }
}
