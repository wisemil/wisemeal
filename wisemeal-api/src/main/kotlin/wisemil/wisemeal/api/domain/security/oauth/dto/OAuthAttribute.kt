package wisemil.wisemeal.api.domain.security.oauth.dto

import wisemil.wisemeal.core.domain.member.model.SignType

data class OAuthAttribute(
    val signType: SignType,
    val email: String,
    val attributes: MutableMap<String, Any>,
    val nameAttributeKey: String?,
) {

    fun addAttribute(key: String, value: Any) {
        attributes[key] = value
    }

    companion object {
        fun from(
            registrationId: String,
            useNameAttributeName: String?,
            attributes: Map<String, Any>,
        ): OAuthAttribute {
            return OAuthAttribute(
                signType = SignType.findByRegistrationId(registrationId),
                email = attributes["email"] as String,
                attributes = attributes.toMutableMap(),
                nameAttributeKey = useNameAttributeName,
            )
        }
    }
}
