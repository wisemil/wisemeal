package wisemil.wisemeal.common.exception.base

import wisemil.wisemeal.common.response.ApiResponseCode

open class WisemealSystemException(
    apiResponseCode: ApiResponseCode = ApiResponseCode.INTERNAL_ERROR,
    logMessage: String = apiResponseCode.message,
) : WisemealException(
    apiResponseCode = apiResponseCode,
    logMessage = logMessage
)
