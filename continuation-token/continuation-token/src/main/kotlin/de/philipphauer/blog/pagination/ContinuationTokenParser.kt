package de.philipphauer.blog.pagination

object ContinuationTokenParser {
    val DELIMITER = ":"
    //TODO test
    //TODO handle error cases
    fun parse(tokenString: String): ContinuationToken{
        val parts = tokenString.split(DELIMITER)
        return ContinuationToken(
                timestamp = parts[0].toLong(),
                offset = parts[1].toInt(),
                checksum = parts[2].toLong()
        )
    }
}
