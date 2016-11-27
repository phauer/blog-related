package de.philipphauer.blog.scaffolding

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import javax.validation.constraints.NotNull

@Configuration
@ConfigurationProperties(prefix = "myapp")
open class MyAppConfig {
    @NotNull lateinit var myprop: String
}