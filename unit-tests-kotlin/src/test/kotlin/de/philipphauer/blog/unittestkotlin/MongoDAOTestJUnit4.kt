package de.philipphauer.blog.unittestkotlin

import org.junit.BeforeClass
import org.junit.Test

//JUnit4. Don't:
class MongoDAOTestJUnit4 {

    companion object {
        @JvmStatic
        private lateinit var mongo: KGenericContainer
        @JvmStatic
        private lateinit var mongoDAO: MongoDAO

        @BeforeClass
        @JvmStatic
        fun initialize() {
            mongo = KGenericContainer("mongo:3.4.3").apply {
                withExposedPorts(27017)
                start()
            }
            mongoDAO = MongoDAO(host = mongo.containerIpAddress, port = mongo.getMappedPort(27017))
        }
    }

    @Test
    fun foo() {
        // test mongoDAO
    }
}

//JUnit4 -> java.lang.Exception: Method init() should be static
//    @BeforeClass
//    fun init(){
//    }
