package de.philipphauer.blog.pagination

import java.util.zip.CRC32

object Pagination{

    //TODO implement checksum

    fun createPage(currentEntitiesSinceIncludingTs: List<Pageable>, oldToken: ContinuationToken?, requiredPageSize: Int): Page {
        if (currentEntitiesSinceIncludingTs.isEmpty()){
            return Page(entities = listOf(), currentToken = null)
        }
        if (oldToken == null || currentPageStartsWithANewTimestampThanInToken(currentEntitiesSinceIncludingTs, oldToken)){
            //don't skip
            val token = createTokenForPage(currentEntitiesSinceIncludingTs, requiredPageSize)
            return Page(
                    entities = currentEntitiesSinceIncludingTs,
                    currentToken = token
            )
        } else {
            val entities = skipOffset(currentEntitiesSinceIncludingTs, oldToken)
            val token = createTokenForPage(currentEntitiesSinceIncludingTs, requiredPageSize)
            return Page(
                    entities = entities,
                    currentToken = token
            )
        }
    }

    private fun fillUpWholePage(entities: List<Pageable>, requiredPageSize: Int): Boolean =
            entities.size >= requiredPageSize

    private fun currentPageStartsWithANewTimestampThanInToken(currentEntitiesSinceIncludingTs: List<Pageable>, oldToken: ContinuationToken): Boolean {
        val timestampOfFirstElement = currentEntitiesSinceIncludingTs.first().getTimestamp()
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

    private fun skipOffset(allEntitiesSinceIncludingTs: List<Pageable>, currentToken: ContinuationToken) =
            allEntitiesSinceIncludingTs.subList(currentToken.offset, allEntitiesSinceIncludingTs.size)

    internal fun createTokenForPage(currentEntitiesSinceIncludingTs: List<Pageable>, requiredPageSize: Int): ContinuationToken? {
        if (currentEntitiesSinceIncludingTs.isEmpty()){
            return null
        }
        if (!fillUpWholePage(currentEntitiesSinceIncludingTs, requiredPageSize)){
            return null // no next token required
        }
        val highestEntities = getEntitiesWithHighestTimestamp(currentEntitiesSinceIncludingTs)
        val highestTimestamp = highestEntities.last().getTimestamp()
        val ids = highestEntities.map(Pageable::getID)
        val checksum = createCRC32Checksum(ids)
        return ContinuationToken(
                timestamp = highestTimestamp,
                offset = highestEntities.size,
                checksum = checksum
        )
    }

    fun createCRC32Checksum(ids: List<String>): Long {
        val hash = CRC32()
        hash.update(ids.joinToString("_").toByteArray())
        return hash.value
    }

    internal fun getEntitiesWithHighestTimestamp(entities: List<Pageable>): List<Pageable> {
        if (entities.isEmpty()){
            return listOf()
        }
        val highestTimestamp = entities.last().getTimestamp()
        val entitiesWithHighestTimestamp = mutableListOf<Pageable>()

        val lastIndex = entities.size - 1
        var i = lastIndex
        while (i >= 0 && highestTimestamp == entities[i].getTimestamp()) {
            entitiesWithHighestTimestamp.add(entities[i])
            i--
        }
        return entitiesWithHighestTimestamp.reversed()
    }

}