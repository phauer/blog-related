package de.philipphauer.blog.scaffolding

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableAutoConfiguration
@EnableScheduling
class MyApplication

fun main(args: Array<String>) {
    SpringApplication.run(MyApplication::class.java, *args)
}
