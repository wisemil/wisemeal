package wisemil.wisemeal.jwt.spec

import com.auth0.jwt.interfaces.Claim

data class JwtPayload(
    val payloads: Map<String, Claim> = emptyMap(),
) {

    fun <T> get(key: String, type: Class<T>): T? {
        return payloads[key]?.`as`(type)
    }
}
