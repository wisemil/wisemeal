package wisemil.wisemeal.common.response

data class ApiResponse<T>(
    val code: String,
    val message: String,
    val data: T? = null,
) {
    companion object {
        private val RESULT_SUCCESS: ApiResponse<Nothing> = of(ApiResponseCode.OK)
        private val RESULT_ERROR: ApiResponse<Nothing> = of(ApiResponseCode.INTERNAL_ERROR)

        fun ok(): ApiResponse<Nothing> {
            return RESULT_SUCCESS
        }

        fun <D> ok(data: D): ApiResponse<D> {
            return of(ApiResponseCode.OK, data)
        }

        fun fail(): ApiResponse<Nothing> {
            return RESULT_ERROR
        }

        fun fail(code: ApiResponseCode): ApiResponse<Nothing> {
            return of(code)
        }

        fun fail(code: ApiResponseCode, message: String?): ApiResponse<Nothing> {
            return of(code, message)
        }

        fun of(responseCode: ApiResponseCode) = ApiResponse(
            code = responseCode.code,
            message = responseCode.message,
            data = null
        )

        fun of(responseCode: ApiResponseCode, message: String?) = ApiResponse(
            code = responseCode.code,
            message = message ?: responseCode.message,
            data = null
        )

        fun <T> of(responseCode: ApiResponseCode, data: T) = ApiResponse(
            code = responseCode.code,
            message = responseCode.message,
            data = data
        )
    }
}
