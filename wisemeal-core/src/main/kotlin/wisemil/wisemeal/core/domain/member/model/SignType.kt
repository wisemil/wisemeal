package wisemil.wisemeal.core.domain.member.model

enum class SignType(val registrationId: String) {
    NAVER("naver"),
    KAKAO("kakao"),
    GOOGLE("google"),
    WISEMEAL("wisemeal"),
    ;

    companion object {
        fun findByRegistrationId(registrationId: String): SignType {
            return values().find { it.registrationId == registrationId }
                ?: throw IllegalArgumentException("registration id not found:$registrationId")
        }
    }
}
