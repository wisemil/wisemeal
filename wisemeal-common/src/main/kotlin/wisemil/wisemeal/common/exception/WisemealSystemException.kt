package wisemil.wisemeal.common.exception

import wisemil.wisemeal.common.response.ApiResponseCode

class WisemealSystemException(
    apiResponseCode: ApiResponseCode = ApiResponseCode.INTERNAL_ERROR,
    logMessage: String = apiResponseCode.message,
) : WisemealException(
    apiResponseCode = apiResponseCode,
    logMessage = logMessage
)
