package wisemil.wisemeal.jwt.impl.oauth0

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import wisemil.wisemeal.jwt.spec.JwtPayload
import wisemil.wisemeal.jwt.spec.JwtToken

class OAuth0JwtToken(
    private val algorithm: Algorithm,
) : JwtToken {
    private val objectMapper = jacksonObjectMapper()

    override fun create(payload: String): String {
        return JWT.create()
            .withIssuer("issuer")
            .withPayload(mapOf("memberNumber" to payload))
            .sign(algorithm)
    }

    override fun decode(token: String): JwtPayload {
        val verifier = JWT.require(algorithm)
            .withIssuer("issuer")
            .build()
        val decodedToken = verifier.verify(token)

        return JwtPayload(decodedToken.claims)
    }

    override fun refresh(token: JwtToken): Boolean {
        TODO("Not yet implemented")
    }

    override fun destroy(token: JwtToken): Boolean {
        TODO("Not yet implemented")
    }
}
