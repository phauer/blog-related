package kotlinVariant

import java.sql.SQLException
import java.util.Locale

//"when" is extremely powerful construct is Kotlin. It's much more than just a switch.

fun getDefaultLocale(deliveryArea: String): Locale {
    when (deliveryArea){
        "germany", "austria", "switzerland" -> return Locale.GERMAN
        "usa", "great britain", "australia" -> return Locale.ENGLISH
        else -> throw IllegalArgumentException("Unsupported deliveryArea $deliveryArea") //string interpolation
    }
}
// or even shorter as a single expression function:
fun getDefaultLocale2(deliveryArea: String) = when (deliveryArea){
    "germany", "austria", "switzerland" -> Locale.GERMAN
    "usa", "great britain", "australia" -> Locale.ENGLISH
    else -> throw IllegalArgumentException("Unsupported deliveryArea $deliveryArea")
}

fun getExceptionMessage(exception: Exception) = when (exception){
    //concise type switches
    is MyLabeledException -> exception.label //smart cast to MyLabeledException -> we can call label directly.
    is SQLException -> "${exception.message}. state: ${exception.sqlState}" //string interpolation
    else -> exception.message
}
class MyLabeledException(val label: String) : RuntimeException(label)