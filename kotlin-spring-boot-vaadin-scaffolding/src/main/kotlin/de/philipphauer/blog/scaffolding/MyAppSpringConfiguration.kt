package de.philipphauer.blog.scaffolding

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import javax.sql.DataSource
import org.springframework.beans.factory.InjectionPoint
import org.springframework.context.annotation.Scope


@Configuration
open class MyAppSpringConfiguration {

    @Bean
    @Scope("prototype")
    open fun logger(injectionPoint: InjectionPoint): Logger {
        return LoggerFactory.getLogger(injectionPoint.methodParameter.containingClass)
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    open fun datasource(): DataSource {
        return DataSourceBuilder.create().build()
    }

    @Bean
    open fun jdbcTemplate(datasource: DataSource) = JdbcTemplate(datasource)

}