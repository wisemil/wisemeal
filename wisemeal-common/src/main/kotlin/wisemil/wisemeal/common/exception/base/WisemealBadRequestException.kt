package wisemil.wisemeal.common.exception.base

import wisemil.wisemeal.common.response.ApiResponseCode

open class WisemealBadRequestException(
    apiResponseCode: ApiResponseCode = ApiResponseCode.BAD_REQUEST,
    logMessage: String = apiResponseCode.message,
) : WisemealException(
    apiResponseCode = apiResponseCode,
    logMessage = logMessage
)
