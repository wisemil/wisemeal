package wisemil.wisemeal.api.domain.security.wisemeal.component

import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import wisemil.wisemeal.common.exception.member.WisemealAccessDeniedException
import wisemil.wisemeal.common.log.logger
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class WisemealAccessDeniedHandler : AccessDeniedHandler {
    private val log = logger()

    override fun handle(request: HttpServletRequest?, response: HttpServletResponse?, accessDeniedException: AccessDeniedException?) {
        log.info("access denied request:{}", request, accessDeniedException)
        throw WisemealAccessDeniedException(accessDeniedException?.message)
    }
}
