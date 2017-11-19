package de.philipphauer.blog.pagination

import java.util.zip.CRC32

object Pagination{

    fun createNextPage(allEntitiesSinceIncludingKey: List<Pageable>, currentToken: ContinuationToken?): Page {
        if (currentToken == null){
            val nextToken = createToken(allEntitiesSinceIncludingKey)
            return Page(
                    entities = allEntitiesSinceIncludingKey,
                    currentToken = null,
                    nextToken = nextToken
            )
        } else {
            //TODO mind checksum
            val entities = skipOffset(allEntitiesSinceIncludingKey, currentToken)
            val nextToken = createToken(entities)
            return Page(
                    entities = entities,
                    currentToken = currentToken,
                    nextToken = nextToken
            )
        }
    }

    //TODO test properly
    fun calculateQueryAdvice(token: ContinuationToken?, pageSize: Int): QueryAdvice {
        if (token == null){
            return QueryAdvice(
                    limit = pageSize,
                    timestamp = 0
            )
        }
        return QueryAdvice(
                limit = token.offset + pageSize,
                timestamp = token.timestamp
        )
    }

    private fun skipOffset(allEntitiesSinceIncludingKey: List<Pageable>, currentToken: ContinuationToken) =
            allEntitiesSinceIncludingKey.subList(currentToken.offset, allEntitiesSinceIncludingKey.size)

    private fun createToken(entities: List<Pageable>): ContinuationToken {
        val highestEntities = getEntitiesWithHighestKey(entities)
        val highestTimestamp = highestEntities.last().getKey()
        val ids = highestEntities.map(Pageable::getIdentifier)
        val checksum = createCRC32Checksum(ids)
        return ContinuationToken(
                timestamp = highestTimestamp,
                offset = highestEntities.size,
                checksum = checksum
        )
    }

    private fun createCRC32Checksum(ids: List<String>): Long {
        val hash = CRC32()
        hash.update(ids.joinToString("_").toByteArray())
        return hash.value
    }

    //TODO test this method dedicated!
    private fun getEntitiesWithHighestKey(entities: List<Pageable>): List<Pageable> {
        val highestTimestamp = entities.last().getKey()
        val entitiesWithHighestTimestamp = mutableListOf<Pageable>()

        val lastIndex = entities.size - 1
        var i = lastIndex
        while (highestTimestamp == entities[i].getKey()) {
            entitiesWithHighestTimestamp.add(entities[i])
            i--
        }
        return entitiesWithHighestTimestamp.reversed()
    }

}