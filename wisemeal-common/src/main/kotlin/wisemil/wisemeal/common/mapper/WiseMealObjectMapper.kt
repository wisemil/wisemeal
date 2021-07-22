package wisemil.wisemeal.common.mapper

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer
import com.fasterxml.jackson.module.kotlin.KotlinModule
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object WiseMealObjectMapper {
    private val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")

    private val javaTimeModule = JavaTimeModule()
        .addDeserializer(LocalDateTime::class.java, LocalDateTimeDeserializer(dateTimeFormatter))
        .addSerializer(LocalDateTime::class.java, LocalDateTimeSerializer(dateTimeFormatter))
        .addDeserializer(LocalDate::class.java, LocalDateDeserializer(dateFormatter))
        .addSerializer(LocalDate::class.java, LocalDateSerializer(dateFormatter))
        .addDeserializer(LocalTime::class.java, LocalTimeDeserializer(timeFormatter))
        .addSerializer(LocalTime::class.java, LocalTimeSerializer(timeFormatter))

    fun getDefaultMapper(): ObjectMapper {
        return ObjectMapper()
            .registerModule(javaTimeModule)
            .registerModule(KotlinModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }
}
