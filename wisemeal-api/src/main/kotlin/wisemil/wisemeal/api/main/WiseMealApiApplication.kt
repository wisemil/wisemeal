package wisemil.wisemeal.api.main

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import
import wisemil.wisemeal.api.config.WiseMealApiConfig

@Import(value = [
    WiseMealApiConfig::class
])
@SpringBootApplication
class WiseMealApiApplication

fun main(vararg args: String) {
    runApplication<WiseMealApiApplication>(*args)
}
