package de.philipphauer.blog.unittestkotlin

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class Mocking {

    @Test
    fun test() {
        val service: TagService = mock()
        whenever(service.getTags(any())).thenReturn(listOf("1", "2"))
        println(service.getTags("foo")) // 1, 2

        verify(service).getTags(any())

        setClient(mock())
    }

    @Test
    fun testPlainMockito() {
        val service = org.mockito.Mockito.mock(TagService::class.java)
        Mockito.`when`(service.getTags(Mockito.any())).thenReturn(listOf("1", "2"))

        println(service.getTags("sadf")) //

        Mockito.verify(service).getTags(Mockito.any())

        setClient(Mockito.mock(Client::class.java))
    }

    fun setClient(client: Client) {

    }
}

open class Client

open class TagService {
    open fun getTags(para: String): List<String> {
        println("getTags impl")
        return listOf("a", "b")
    }
}