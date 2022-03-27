package wisemil.wisemeal.api.domain

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheckController {
    @GetMapping("/health")
    fun healthCheck(): Map<String, String> {
        return mapOf("status" to "OK")
    }
}
