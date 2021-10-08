package wisemil.wisemeal.jwt.config.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("jwt.algorithms")
class JwtProperties(
    var type: String? = null,

    var secret: String? = null,
)
