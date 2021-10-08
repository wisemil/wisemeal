package wisemil.wisemeal.jwt.config

import com.auth0.jwt.algorithms.Algorithm
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import wisemil.wisemeal.jwt.config.property.JwtProperties
import wisemil.wisemeal.jwt.impl.oauth0.OAuth0JwtToken
import wisemil.wisemeal.jwt.spec.JwtToken

@Configuration
class JwtConfig {

    @Configuration
    @EnableConfigurationProperties(value = [JwtProperties::class])
    class OAuth0JwtConfig {
        @Bean
        fun jwtToken(properties: JwtProperties): JwtToken {
            return OAuth0JwtToken(Algorithm.HMAC256(properties.secret!!))
        }
    }

}
