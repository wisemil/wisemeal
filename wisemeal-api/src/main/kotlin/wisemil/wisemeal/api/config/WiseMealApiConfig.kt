package wisemil.wisemeal.api.config

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@ComponentScan(
    basePackages = [
        "wisemil.wisemeal.api.domain"
    ]
)
@Configuration
class WiseMealApiConfig
