package wisemil.wisemeal.api.test

import org.springframework.restdocs.snippet.Attributes
import java.util.Arrays
import java.util.function.Function
import java.util.stream.Collectors

object ApiDocumentFormatGenerator {
    fun required(): Attributes.Attribute {
        return Attributes.key("required").value("true")
    }

    fun optional(): Attributes.Attribute {
        return Attributes.key("required").value("false")
    }

    fun customFormat(format: String): Attributes.Attribute {
        return Attributes.key("format").value(format)
    }

    fun emptyFormat(): Attributes.Attribute {
        return customFormat("")
    }

    fun dateFormat(): Attributes.Attribute {
        return customFormat("yyyy-MM-dd")
    }

    fun dateTimeFormat(): Attributes.Attribute {
        return customFormat("yyyy-MM-dd HH:mm:ss")
    }

    fun <E : Enum<E>?> generate(enumClass: Class<E>): String {
        return enumClass.enumConstants.joinToString(separator = "\n")
    }

    fun defaultValue(defaultValue: String?): Attributes.Attribute? {
        return Attributes.key("defaultValue").value(defaultValue)
    }

    fun emptyDefaultValue(): Attributes.Attribute? {
        return Attributes.key("defaultValue").value("")
    }

    fun dateFormatWithPattern(pattern: String?): Attributes.Attribute? {
        return Attributes.key("format").value(pattern)
    }

    fun <T : Enum<T>?> enumFormat(enums: Class<T>): Attributes.Attribute? {
        return Attributes.key("format")
            .value(Arrays.stream(enums.enumConstants).map { obj: T -> obj!!.name }
                .collect(Collectors.joining("\n")))
    }

    fun <T : Enum<T>?> enumFormat(enums: Collection<T>): Attributes.Attribute? {
        return Attributes.key("format")
            .value(enums.stream().map { obj: T -> obj!!.name }.collect(Collectors.joining("\n")))
    }

    fun <T : Enum<T>?> enumFormat(enums: Array<T>?, detailFunction: Function<T, String>): Attributes.Attribute? {
        return Attributes.key("format").value(
            Arrays.stream(enums).collect(Collectors.toList()).stream()
                .map { en: T -> en!!.name + "(" + detailFunction.apply(en) + ")" }.collect(Collectors.joining("\n"))
        )
    }

    fun <T : Enum<T>?> enumFormat(
        enums: Collection<T>,
        detailFunction: Function<T, String>,
    ): Attributes.Attribute? {
        return Attributes.key("format")
            .value(enums.stream().map { en: T -> en!!.name + "(" + detailFunction.apply(en) + ")" }
                .collect(Collectors.joining("\n")))
    }
}
