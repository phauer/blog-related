package functions

import org.json.JSONException
import org.json.JSONObject

val url = "http://bla.de?asdf"

val delimiterIndex = url.indexOf("?")
val strippedUrl = if (delimiterIndex > 0) {
    url.substring(0, delimiterIndex)
} else {
    url
}

val json = """{"message":"HELLO"}"""
val message = try {
    JSONObject(json).getString("message")
} catch (ex: JSONException) {
    json
}

fun getMessage(json: String): String {
    val message: String = try {
        JSONObject(json).getString("message")
    } catch (ex: JSONException) {
        json
    }
    return message
}
fun getMessage2(json: String) = try {
    JSONObject(json).getString("message")
} catch (ex: JSONException) {
    json
}
