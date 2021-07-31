package wisemil.wisemeal.common.customizer

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import wisemil.wisemeal.common.format.LocalDateTimeExtensions

object JsonCustomizer {
    fun jsonCustomizer(): Jackson2ObjectMapperBuilderCustomizer {
        return Jackson2ObjectMapperBuilderCustomizer { builder ->
            builder.serializers(LocalDateTimeSerializer(LocalDateTimeExtensions.DATE_TIME_FORMAT))
            builder.deserializers(LocalDateTimeDeserializer(LocalDateTimeExtensions.DATE_TIME_FORMAT))
        }
    }
}
