package idiomaticKotlin

// due to `to` infix function to create a Pair and static methods to create lists and maps, defining structs is reasonable in kotlin.
// but still not as good as in Python or JavaScript. But it's ok and way better then in Java.
val customer = mapOf(
        "name" to "Clair Grube",
        "age" to 30,
        "languages" to listOf("german", "english"),
        "address" to mapOf(
                "city" to "Leipzig",
                "street" to "Karl-Liebknecht-Straße 1",
                "zipCode" to "04107"
        )
)