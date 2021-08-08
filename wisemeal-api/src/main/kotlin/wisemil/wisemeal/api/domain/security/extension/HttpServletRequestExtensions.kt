package wisemil.wisemeal.api.domain.security.extension

import wisemil.wisemeal.api.domain.security.oauth.component.HttpCookieOAuth2AuthorizationRequestRepository.Companion.WISEMIL_REDIRECT_URI
import javax.servlet.http.HttpServletRequest

fun HttpServletRequest.redirectUri(): String? {
    return this.cookie(WISEMIL_REDIRECT_URI)?.value
}
