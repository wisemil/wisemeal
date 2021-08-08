package wisemil.wisemeal.api.domain.security.oauth.component

import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest
import org.springframework.stereotype.Component
import wisemil.wisemeal.api.domain.security.extension.cookie
import wisemil.wisemeal.api.domain.security.extension.deleteCookie
import wisemil.wisemeal.api.domain.security.extension.putCookie
import wisemil.wisemeal.common.encode.deserializeBase64
import wisemil.wisemeal.common.encode.serializeBase64
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class HttpCookieOAuth2AuthorizationRequestRepository : AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    override fun loadAuthorizationRequest(request: HttpServletRequest?): OAuth2AuthorizationRequest? {
        return request?.cookie(WISEMIL_AUTH_REQUEST)
            ?.value
            ?.deserializeBase64(OAuth2AuthorizationRequest::class.java)
    }

    override fun saveAuthorizationRequest(
        authorizationRequest: OAuth2AuthorizationRequest?,
        request: HttpServletRequest,
        response: HttpServletResponse,
    ) {
        if (authorizationRequest == null) {
            removeAuthorizationCookies(request, response)
            return
        }

        response.putCookie(WISEMIL_AUTH_REQUEST, authorizationRequest.serializeBase64())

        request.getParameter(WISEMIL_REDIRECT_URI)
            ?.run { // redirect_uri in request parameters
                response.putCookie(WISEMIL_REDIRECT_URI, this)
            }
    }

    override fun removeAuthorizationRequest(request: HttpServletRequest): OAuth2AuthorizationRequest? {
        return this.loadAuthorizationRequest(request)
    }

    fun removeAuthorizationCookies(request: HttpServletRequest, response: HttpServletResponse) {
        response.deleteCookie(request, WISEMIL_AUTH_REQUEST)
        response.deleteCookie(request, WISEMIL_REDIRECT_URI)
    }

    companion object {
        private const val WISEMIL_AUTH_REQUEST = "wisemil-auth-request"
        const val WISEMIL_REDIRECT_URI = "redirect_uri"
    }
}
