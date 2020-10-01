package com.phauer.unittestkotlin

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

//Do:
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MongoDAOTestJUnit5 {
    private val mongo = KGenericContainer("mongo:3.4.3").apply {
        withExposedPorts(27017)
        start()
    }
    private val mongoDAO = MongoDAO(host = mongo.containerIpAddress, port = mongo.getMappedPort(27017))

    @Test
    fun foo() {
        // test mongoDAO
    }
}

//Do:
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MongoDAOTestJUnit5Constructor {
    private val mongo: KGenericContainer
    private val mongoDAO: MongoDAO

    init {
        mongo = KGenericContainer("mongo:3.4.3").apply {
            withExposedPorts(27017)
            start()
        }
        mongoDAO = MongoDAO(host = mongo.containerIpAddress, port = mongo.getMappedPort(27017))
    }

    @Test
    fun foo() {
        // test mongoDAO
    }
}

//Don't:
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MongoDAOTestJUnit5BeforeAll {
    private val mongo: KGenericContainer
    private val mongoDAO: MongoDAO

    init {
        mongo = KGenericContainer("mongo:3.4.3").apply {
            withExposedPorts(27017)
            start()
        }
        mongoDAO = MongoDAO(host = mongo.containerIpAddress, port = mongo.getMappedPort(27017))
    }

    @Test
    fun foo() {
        // test mongoDAO
    }
}