package wisemil.wisemeal.common.converter

import org.springframework.core.convert.converter.Converter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LocalDateConverter(
    private val format: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

): Converter<String, LocalDate> {
    override fun convert(source: String?): LocalDate? {
        return if (source?.trim().isNullOrBlank()) {
            null
        } else {
            LocalDate.parse(source, format)
        }
    }
}
