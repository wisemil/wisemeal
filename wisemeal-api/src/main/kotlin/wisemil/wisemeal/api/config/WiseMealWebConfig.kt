package wisemil.wisemeal.api.config

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import wisemil.wisemeal.common.converter.WebParameterConverter
import wisemil.wisemeal.common.customizer.JsonCustomizer

@Configuration
class WiseMealWebConfig : WebMvcConfigurer {
    override fun addFormatters(registry: FormatterRegistry) {
        for (parameterBinder in WebParameterConverter.getParameterBinders()) {
            registry.addConverter(parameterBinder)
        }
    }

    @Bean
    fun jsonCustomizer(): Jackson2ObjectMapperBuilderCustomizer {
        return JsonCustomizer.jsonCustomizer()
    }
}
