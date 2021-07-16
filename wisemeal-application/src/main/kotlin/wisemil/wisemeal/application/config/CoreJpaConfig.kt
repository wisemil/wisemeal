package wisemil.wisemeal.application.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EntityScan(value = [
    "wisemil.wisemeal.core.domain"
])
@EnableJpaRepositories(value = [
    "wisemil.wisemeal.application.domain"
])
@EnableJpaAuditing
@Configuration
class CoreJpaConfig
