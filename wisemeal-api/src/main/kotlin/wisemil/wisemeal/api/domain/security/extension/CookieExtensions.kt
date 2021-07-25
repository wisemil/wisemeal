package wisemil.wisemeal.api.domain.security.extension

import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

fun HttpServletRequest.cookie(name: String): Cookie? {
    return this.cookies?.find { it.name == name }
}

fun HttpServletResponse.putCookie(name: String, value: String?, maxAge: Int = 180) {
    val cookie = Cookie(name, value)
    cookie.path = "/"
    cookie.isHttpOnly = true
    cookie.maxAge = maxAge
    this.addCookie(cookie)
}

fun HttpServletResponse.deleteCookie(request: HttpServletRequest, name: String) {
    request.cookies?.forEach { cookie ->
        if (cookie.name == name) {
            cookie.value = ""
            cookie.path = "/"
            cookie.maxAge = 0
            this.addCookie(cookie)
        }
    }
}

