package wisemil.wisemeal.common.exception.member

import wisemil.wisemeal.common.exception.base.WisemealBadRequestException
import wisemil.wisemeal.common.response.ApiResponseCode

class WisemealAccessDeniedException(logMessage: String? = null) : WisemealBadRequestException(
    apiResponseCode = ApiResponseCode.ACCESS_DENIED,
    logMessage = logMessage ?: ApiResponseCode.ACCESS_DENIED.message)
