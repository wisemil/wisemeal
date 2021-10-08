package wisemil.wisemeal.jwt.spec

interface JwtToken {
    fun create(payload: String): String

    fun decode(token: String): JwtPayload

    fun refresh(token: JwtToken): Boolean

    fun destroy(token: JwtToken): Boolean
}
