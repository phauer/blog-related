package de.philipphauer.blog.scaffolding

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import javax.validation.constraints.NotNull

@Configuration
@ConfigurationProperties(prefix = "myapp")
open class MyAppConfig {
    @NotNull lateinit var requiredProp: String
    var optionalProp: String? = null
}

@Configuration
@ConfigurationProperties(prefix = "myapp.authentication")
open class AuthenticationConfig {
    @NotNull lateinit var url: String
    var credentials: Credentials? = null
}
class Credentials{
    @NotNull lateinit var userName: String
    @NotNull lateinit var userPassword: String
}

