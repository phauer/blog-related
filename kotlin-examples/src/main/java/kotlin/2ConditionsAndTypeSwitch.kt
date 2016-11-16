package kotlin

import java.sql.SQLException
import java.util.Locale

//"when" is extremely powerful construct is Kotlin. It's much more than just a switch.

fun getDefaultLocale(deliveryArea: String): Locale {
    when (deliveryArea){
        "germany", "austria", "switzerland" -> return Locale.GERMAN
        "usa", "great britain", "australia" -> return Locale.ENGLISH
        else -> throw IllegalStateException("Unsupported deliveryArea $deliveryArea")
    }
}
// or shorter as a single expression function:
fun getDefaultLocale2(deliveryArea: String): Locale = when (deliveryArea){
    "germany", "austria", "switzerland" -> Locale.GERMAN
    "usa", "england", "australia" -> Locale.ENGLISH
    else -> throw IllegalStateException("Unsupported deliveryArea $deliveryArea")
}

fun getExceptionMessage(exception: Exception) = when (exception){
    //concise type switches
    is MyLabeledException -> exception.label //smart cast to SQLException -> we can call sqlState directly.
    is SQLException -> "${exception.message}. state: ${exception.sqlState}" //string interpolation
    else -> exception.message
}

class MyLabeledException(val label: String) : RuntimeException(label)