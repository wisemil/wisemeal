package wisemil.wisemeal.common.response

enum class ApiResponseCode(
    val code: String,
    val message: String,
) {
    OK("0000", "성공"),

    BAD_REQUEST("E400", "잘못된 요청"),

    INTERNAL_ERROR("I999", "서버 내부 오류"),
}
