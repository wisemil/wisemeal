package wisemil.wisemeal.api.domain.security.wisemeal.component

import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder
import wisemil.wisemeal.api.domain.security.extension.cookie
import wisemil.wisemeal.api.domain.security.oauth.component.HttpCookieOAuth2AuthorizationRequestRepository
import wisemil.wisemeal.common.log.logger
import java.net.URI
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
abstract class WisemealAuthenticationSuccessHandler : SimpleUrlAuthenticationSuccessHandler() {
    private val log = logger()

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication?,
    ) {
        responseAfterAuthenticationSuccess(request, response, authentication)
    }

    abstract fun responseAfterAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication?,
    )

    override fun determineTargetUrl(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication,
    ): String {
        val redirectUri = request.cookie(HttpCookieOAuth2AuthorizationRequestRepository.WISEMIL_REDIRECT_URI)?.value
        log.debug { "redirect_uri:${redirectUri}" }

        redirectUri?.run { checkAuthorizedRedirectUri(this) }

        val targetUrl: String = redirectUri ?: defaultTargetUrl
        return UriComponentsBuilder.fromUriString(targetUrl)
            .build().toUriString()
    }

    private fun checkAuthorizedRedirectUri(redirectUri: String) {
        val clientRedirectUri = URI.create(redirectUri)
        // TODO 등록된 redirect uri 만 허용하도록
    }

    protected fun clearAuthentication(request: HttpServletRequest, response: HttpServletResponse) {
        super.clearAuthenticationAttributes(request)
    }
}
