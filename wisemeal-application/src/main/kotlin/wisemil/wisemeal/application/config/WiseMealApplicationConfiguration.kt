package wisemil.wisemeal.application.config

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Import(
    value = [
        CoreJpaConfig::class,
        QuerydslConfig::class,
    ]
)
@ComponentScan(value = [
    "wisemil.wisemeal.application.domain",
])
@Configuration
class WiseMealApplicationConfiguration
