package de.philipphauer.blog.pagination

import java.util.zip.CRC32

object Pagination{

    fun createPage(currentEntitiesSinceIncludingKey: List<Pageable>, oldToken: ContinuationToken?, requiredPageSize: Int): Page {
        if (currentEntitiesSinceIncludingKey.size < requiredPageSize){
            return Page(
                    entities = currentEntitiesSinceIncludingKey,
                    currentToken = null
            )
        }
        if (oldToken == null || currentPageStartsWithANewTimestampThanInToken(currentEntitiesSinceIncludingKey, oldToken)){
            //don't skip
            val token = createTokenForPage(currentEntitiesSinceIncludingKey)
            return Page(
                    entities = currentEntitiesSinceIncludingKey,
                    currentToken = token
            )
        } else {
            //TODO mind checksum
            val entities = skipOffset(currentEntitiesSinceIncludingKey, oldToken)
            val token = createTokenForPage(entities)
            return Page(
                    entities = entities,
                    currentToken = token
            )
        }
    }

    private fun currentPageStartsWithANewTimestampThanInToken(currentEntitiesSinceIncludingKey: List<Pageable>, oldToken: ContinuationToken): Boolean {
        val timestampOfFirstElement = currentEntitiesSinceIncludingKey.first().getKey()
        return timestampOfFirstElement != oldToken.timestamp
    }

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

    internal fun createTokenForPage(entities: List<Pageable>): ContinuationToken? {
        if (entities.isEmpty()){
            return null
        }
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

    internal fun createCRC32Checksum(ids: List<String>): Long {
        val hash = CRC32()
        hash.update(ids.joinToString("_").toByteArray())
        return hash.value
    }

    internal fun getEntitiesWithHighestKey(entities: List<Pageable>): List<Pageable> {
        if (entities.isEmpty()){
            return listOf()
        }
        val highestTimestamp = entities.last().getKey()
        val entitiesWithHighestTimestamp = mutableListOf<Pageable>()

        val lastIndex = entities.size - 1
        var i = lastIndex
        while (i >= 0 && highestTimestamp == entities[i].getKey()) {
            entitiesWithHighestTimestamp.add(entities[i])
            i--
        }
        return entitiesWithHighestTimestamp.reversed()
    }

}