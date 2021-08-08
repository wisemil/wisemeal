package wisemil.wisemeal.core.domain.member.model

enum class MemberStatus {
    ENABLE,
    PENDING,
    DISABLE,
    BLOCK,
    ;

    fun isBlock(): Boolean {
        return this == BLOCK
    }
}
