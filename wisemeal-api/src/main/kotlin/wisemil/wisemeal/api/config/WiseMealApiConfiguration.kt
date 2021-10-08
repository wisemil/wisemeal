package wisemil.wisemeal.api.config

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import wisemil.wisemeal.application.config.WiseMealApplicationConfiguration
import wisemil.wisemeal.jwt.config.JwtConfig

@Import(value = [
    WiseMealApplicationConfiguration::class,
    WiseMealWebConfig::class,
    SecurityConfig::class,
    JwtConfig::class,
])
@Configuration
@ComponentScan(value = [
    "wisemil.wisemeal.api.domain"
])
class WiseMealApiConfiguration
