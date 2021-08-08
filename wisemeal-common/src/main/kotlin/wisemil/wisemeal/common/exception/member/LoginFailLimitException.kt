package wisemil.wisemeal.common.exception.member

import wisemil.wisemeal.common.exception.base.WisemealBadRequestException
import wisemil.wisemeal.common.response.ApiResponseCode

class LoginFailLimitException(logMessage: String? = null) : WisemealBadRequestException(
    apiResponseCode = ApiResponseCode.LOGIN_FAIL_LIMIT,
    logMessage = logMessage ?: ApiResponseCode.LOGIN_FAIL_LIMIT.message
) {
}
