package wisemil.wisemeal.admin.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import wisemil.wisemeal.application.config.WiseMealApplicationConfiguration

@Import(value = [
    WiseMealApplicationConfiguration::class
])
@Configuration
class WiseMealAdminConfiguration
