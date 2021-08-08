package wisemil.wisemeal.common.exception.member

import wisemil.wisemeal.common.exception.base.WisemealBadRequestException
import wisemil.wisemeal.common.response.ApiResponseCode

class IncorrectPasswordException(
    logMessage: String? = null,
    apiResponseCode: ApiResponseCode = ApiResponseCode.INCORRECT_PASSWORD,
) : WisemealBadRequestException(
    apiResponseCode = apiResponseCode,
    logMessage = logMessage ?: apiResponseCode.message
)
