package wisemil.wisemeal.api.config

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import wisemil.wisemeal.common.converter.WebParameterConverter
import wisemil.wisemeal.common.format.LocalDateTimeExtensions.DATE_TIME_FORMAT

@Configuration
class WiseMealWebConfig : WebMvcConfigurer {
    override fun addFormatters(registry: FormatterRegistry) {
        for (parameterBinder in WebParameterConverter.getParameterBinders()) {
            registry.addConverter(parameterBinder)
        }
    }

    @Bean
    fun jsonCustomizer(): Jackson2ObjectMapperBuilderCustomizer {
        return Jackson2ObjectMapperBuilderCustomizer { builder ->
            builder.serializers(LocalDateTimeSerializer(DATE_TIME_FORMAT))
            builder.deserializers(LocalDateTimeDeserializer(DATE_TIME_FORMAT))
        }
    }
}
