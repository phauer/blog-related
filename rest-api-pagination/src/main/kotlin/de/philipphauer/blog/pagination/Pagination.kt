package de.philipphauer.blog.pagination

import java.util.zip.CRC32

object Pagination{

    fun createToken(objects: List<Pageable>): ContinuationToken {
        val highestObjects = getObjectsWithHighestKey(objects)
        val highestTimestamp = highestObjects.last().getKey()
        val ids = highestObjects.map(Pageable::getIdentifier)
        val checksum = createCRC32Checksum(ids)
        return ContinuationToken(
                timestamp = highestTimestamp,
                offset = highestObjects.size,
                checksum = checksum
        )
    }

    private fun createCRC32Checksum(ids: List<String>): Long {
        val hash = CRC32()
        hash.update(ids.joinToString("_").toByteArray())
        return hash.value
    }

    //TODO test this method dedicated! it's broken! but essential
    private fun getObjectsWithHighestKey(objects: List<Pageable>): List<Pageable> {
        val highestTimestamp = objects.last().getKey()
        val objectsWithHighestTimestamp = mutableListOf<Pageable>()

        val lastIndex = objects.size - 1
        var i = lastIndex
        while (highestTimestamp == objects[i].getKey()) {
            objectsWithHighestTimestamp.add(objects[i])
            i--
        }
        return objectsWithHighestTimestamp.reversed()
    }

}