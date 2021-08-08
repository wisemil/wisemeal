package wisemil.wisemeal.common.exception.member

import wisemil.wisemeal.common.exception.base.WisemealBadRequestException
import wisemil.wisemeal.common.response.ApiResponseCode

class MemberNotFoundException(logMessage: String? = null) : WisemealBadRequestException(
    apiResponseCode = ApiResponseCode.MEMBER_NOT_FOUND,
    logMessage = logMessage ?: ApiResponseCode.MEMBER_NOT_FOUND.message
)
