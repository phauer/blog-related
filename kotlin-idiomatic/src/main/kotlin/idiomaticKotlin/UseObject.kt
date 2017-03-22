package idiomaticKotlin

import com.vaadin.data.Converter
import com.vaadin.data.Result
import com.vaadin.data.ValueContext
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale

// use object for singletons or implemention of a framework interface without state.
// here: Vaadin 8's Converter interface
object StringToInstantConverter : Converter<String, Instant> {
    private val DATE_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss Z")
            .withLocale( Locale.UK )
            .withZone( ZoneOffset.UTC )

    override fun convertToModel(value: String?, context: ValueContext?) = try {
        Result.ok(Instant.from(DATE_FORMATTER.parse(value)))
    } catch (ex: DateTimeParseException) {
        Result.error<Instant>(ex.message)
    }

    override fun convertToPresentation(value: Instant?, context: ValueContext?) =
            DATE_FORMATTER.format(value)
}

fun main(args: Array<String>) {
    val i = StringToInstantConverter.convertToPresentation(Instant.now(), null)
    println(i)
    val x = StringToInstantConverter.convertToModel(i, null)
    println(x)
}