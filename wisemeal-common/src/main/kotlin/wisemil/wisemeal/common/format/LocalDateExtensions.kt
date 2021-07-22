package wisemil.wisemeal.common.format

import java.time.format.DateTimeFormatter

object LocalDateExtensions {
    val DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val CUT_YEAR_SHORT_DATE_FORMAT = DateTimeFormatter.ofPattern("yyMMdd")
}
