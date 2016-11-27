package de.philipphauer.blog.scaffolding

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import javax.sql.DataSource


@Configuration
open class MyAppSpringConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    open fun datasource(): DataSource {
        return DataSourceBuilder.create().build()
    }

    @Bean
    open fun jdbcTemplate(datasource: DataSource) = JdbcTemplate(datasource)

}