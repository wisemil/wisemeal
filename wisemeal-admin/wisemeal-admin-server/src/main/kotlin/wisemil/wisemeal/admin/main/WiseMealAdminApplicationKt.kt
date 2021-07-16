package wisemil.wisemeal.admin.main

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import
import wisemil.wisemeal.admin.config.WiseMealAdminConfiguration

@Import(value = [
    WiseMealAdminConfiguration::class
])
@SpringBootApplication
class WiseMealAdminApplicationKt

fun main(vararg args: String) {
    runApplication<WiseMealAdminApplicationKt>(*args)
}
