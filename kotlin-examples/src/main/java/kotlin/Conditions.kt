package kotlin

import java.util.Locale

fun getDefaultLocale(deliveryArea: String): Locale {
    when (deliveryArea){
        "germany", "austria", "switzerland" -> return Locale.GERMAN
        "usa", "england", "australia" -> return Locale.ENGLISH
        else -> throw IllegalStateException("Unsupported deliveryArea $deliveryArea")
    }
}
// or shorter:
fun getDefaultLocale2(deliveryArea: String): Locale = when (deliveryArea){
    "germany", "austria", "switzerland" -> Locale.GERMAN
    "usa", "england", "australia" -> Locale.ENGLISH
    else -> throw IllegalStateException("Unsupported deliveryArea $deliveryArea")
}