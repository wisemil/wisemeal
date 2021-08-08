package wisemil.wisemeal.common.response

/**
 * 성공 0000
 *
 * 의도한 실패 E
 *
 * 의도하지 않거나 제어 불가능한 에러 I
 */
enum class ApiResponseCode(
    val code: String,
    val message: String,
) {
    OK("0000", "성공"),

    MEMBER_NOT_FOUND("E1000", "사용자를 찾지 못하였습니다."),
    INCORRECT_PASSWORD("E1001", "비밀번호가 올바르지 않습니다."),
    LOGIN_FAIL_LIMIT("E1002", "최대 로그인 시도 횟수를 초과했습니다."),
    ALREADY_SIGN_UP("E1003", "이미 가입된 아이디입니다."),

    ACCESS_DENIED("E1100", "권한이 없습니다."),

    BAD_REQUEST("E9999", "잘못된 요청"),

    INTERNAL_ERROR("I9999", "서버 내부 오류"),
}
