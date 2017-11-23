package de.philipphauer.blog.pagination

import java.util.zip.CRC32

object Pagination{

    //TODO implement checksum

    fun createPage(entitiesSinceIncludingTs: List<Pageable>, oldToken: ContinuationToken?, requiredPageSize: Int): Page {
        if (entitiesSinceIncludingTs.isEmpty()){
            return Page(entities = listOf(), currentToken = null)
        }
        if (oldToken == null || currentPageStartsWithANewTimestampThanInToken(entitiesSinceIncludingTs, oldToken)){
            //don't skip
            val token = createTokenForPage(entitiesSinceIncludingTs, entitiesSinceIncludingTs, requiredPageSize)
            return Page(
                    entities = entitiesSinceIncludingTs,
                    currentToken = token
            )
        } else {
            val entitiesForNextPage = skipOffset(entitiesSinceIncludingTs, oldToken)
            val token = createTokenForPage(entitiesSinceIncludingTs, entitiesForNextPage, requiredPageSize)
            return Page(
                    entities = entitiesForNextPage,
                    currentToken = token
            )
        }
    }

    private fun fillUpWholePage(entities: List<Pageable>, requiredPageSize: Int): Boolean =
            entities.size >= requiredPageSize

    private fun currentPageStartsWithANewTimestampThanInToken(allEntitiesSinceIncludingTs: List<Pageable>, oldToken: ContinuationToken): Boolean {
        val timestampOfFirstElement = allEntitiesSinceIncludingTs.first().getTimestamp()
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

    private fun skipOffset(entitiesSinceIncludingTs: List<Pageable>, currentToken: ContinuationToken) =
            entitiesSinceIncludingTs.subList(currentToken.offset, entitiesSinceIncludingTs.size)

    /**
     * @param entitiesForNextPage includes skip/offset
     */
    internal fun createTokenForPage(allEntitiesSinceIncludingTs: List<Pageable>,
                                    entitiesForNextPage: List<Pageable>,
                                    requiredPageSize: Int): ContinuationToken? {
        if (allEntitiesSinceIncludingTs.isEmpty()){
            return null
        }
        if (!fillUpWholePage(entitiesForNextPage, requiredPageSize)){
            return null // no next token required
        }
        val highestEntities = getEntitiesWithHighestTimestamp(allEntitiesSinceIncludingTs)
        val highestTimestamp = highestEntities.last().getTimestamp()
        val ids = highestEntities.map(Pageable::getID)
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