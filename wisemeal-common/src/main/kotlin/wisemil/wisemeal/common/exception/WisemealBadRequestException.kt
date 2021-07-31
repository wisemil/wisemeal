package wisemil.wisemeal.common.exception

import wisemil.wisemeal.common.response.ApiResponseCode

class WisemealBadRequestException(
    apiResponseCode: ApiResponseCode = ApiResponseCode.BAD_REQUEST,
    logMessage: String = apiResponseCode.message,
) : WisemealException(
    apiResponseCode = apiResponseCode,
    logMessage = logMessage
)
