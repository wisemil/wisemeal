package wisemil.wisemeal.api.main

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import
import wisemil.wisemeal.api.config.WiseMealApiConfiguration

@Import(WiseMealApiConfiguration::class)
@SpringBootApplication
class WiseMealApiApplication

fun main(vararg args: String) {
    runApplication<WiseMealApiApplication>(*args)
}
