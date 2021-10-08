package wisemil.wisemeal.api.domain.security.oauth.http

import org.springframework.http.HttpHeaders
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

class OAuthHttpResponse(
    val delegate: HttpServletResponse,
) {
    fun addBearer(value: String) {
        delegate.addHeader(HttpHeaders.AUTHORIZATION, "Bearer $value")
    }

    fun addCookie(name: String, value: String) {
        delegate.addCookie(Cookie(name, value))
    }
}
