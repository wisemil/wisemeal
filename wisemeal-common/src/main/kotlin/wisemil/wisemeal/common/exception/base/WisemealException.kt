package wisemil.wisemeal.common.exception.base

import wisemil.wisemeal.common.response.ApiResponseCode

abstract class WisemealException(
    val apiResponseCode: ApiResponseCode,
    val logMessage: String,
) : RuntimeException(apiResponseCode.message)
