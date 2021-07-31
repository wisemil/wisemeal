package wisemil.wisemeal.api.test

import org.junit.jupiter.api.Tag
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import wisemil.wisemeal.api.main.WiseMealApiApplication

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Tag("integration")
@ActiveProfiles("test")
@SpringBootTest(classes = [WiseMealApiApplication::class])
@AutoConfigureMockMvc
annotation class ApiSpringTest
