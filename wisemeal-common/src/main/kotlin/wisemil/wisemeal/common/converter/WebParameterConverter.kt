package wisemil.wisemeal.common.converter

import org.springframework.core.convert.converter.Converter
import java.time.format.DateTimeFormatter

object WebParameterConverter {
    private val DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    fun getParameterBinders(): List<Converter<*, *>> {
        return listOf(LocalDateConverter(DATE_FORMAT))
    }
}
