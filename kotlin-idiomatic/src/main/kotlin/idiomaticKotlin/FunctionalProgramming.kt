package idiomaticKotlin

import com.jayway.jsonpath.JsonPath
import java.util.Locale

// # Take advantages of functional programming support in Kotlin (better support then in java due to immutability and expression)
// -> reduce side-effects (less error-prone, easier to understand, thread-safe)
// (start with an enumeration of the relevant ##-points)

// ## use immutability (val for variables and properties, immutable data classes, copy(), kotlin's collection api (read-only))
data class Person(var name: String)
//better:
data class Person2(val name: String)

//var x = "hi"
//// better:
//val y = "hallo"

// ## use pure functions (without side-effects) where ever possible (therefore, use expressions and single expression functions)
// ## use if, when, try-catch, single expression function! -> concise, expressive, stateless
// expression instead of statements  (if, when) -> combine control structure with other expression concisely
// Don't:
fun getDefaultLocale(deliveryArea: String): Locale {
    val deliverAreaLower = deliveryArea.toLowerCase()
    if (deliverAreaLower == "germany" || deliverAreaLower == "austria") {
        return Locale.GERMAN
    }
    if (deliverAreaLower == "usa" || deliverAreaLower == "great britain") {
        return Locale.ENGLISH
    }
    if (deliverAreaLower == "french") {
        return Locale.FRENCH
    }
    return Locale.ENGLISH
}

// Do:
fun getDefaultLocale2(deliveryArea: String) = when (deliveryArea.toLowerCase()) {
    "germany", "austria" -> Locale.GERMAN
    "usa", "great britain" -> Locale.ENGLISH
    "french" -> Locale.FRENCH
    else -> Locale.ENGLISH
}
//println(getDefaultLocale("germany"))
// in general: consider if an `if` can be replace with a more concise `when` expression.

//try-catch is also an expression!
val json = """{"message":"HELLO"}"""
val message: String = try {
    JsonPath.parse(json).read("message")
} catch (ex: Exception) {
    json
}
//println(getMessage("""{"message":"HELLO"}""")) //hello

// ## use lambda expression to pass around blocks of code.


fun main(args: Array<String>) {
    println(getDefaultLocale("germany"))
    println(message) //hello
}
