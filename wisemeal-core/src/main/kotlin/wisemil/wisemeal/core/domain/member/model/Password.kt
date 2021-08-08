package wisemil.wisemeal.core.domain.member.model

class Password(
    private val rawPassword: String,
    private val passwordMatcher: (String, String) -> Boolean,
) {
    fun isNotMatch(encPassword: String): Boolean {
        return !match(encPassword)
    }

    fun match(encPassword: String): Boolean {
        return passwordMatcher.invoke(rawPassword, encPassword)
    }
}
