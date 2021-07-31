package wisemil.wisemeal.common.response

enum class ApiResponseCode(
    val code: String,
    val message: String,
) {
    OK("0000", "성공"),
    INTERNAL_ERROR("E999", "서버 내부 오류"),
}
