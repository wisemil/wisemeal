package wisemil.wisemeal.api.config

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(value = [
    "wisemil.wisemeal.api.domain"
])
class WiseMealApiConfiguration
