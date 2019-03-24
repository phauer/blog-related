package com.phauer.modernunittesting;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SchemaCreator implements ApplicationRunner {

    private final JdbcTemplate jdbcTemplate;

    public SchemaCreator(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        createSchema(jdbcTemplate);

        jdbcTemplate.execute("insert into products(id, name) values " +
                "('1', 'notebook'), " +
                "('2', 'smartphone'), " +
                "('3', 'cup') " +
                "ON CONFLICT(id) DO NOTHING;");
    }

    public static void createSchema(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.execute("Create Table IF NOT EXISTS products (id varchar(40) primary key, name varchar(40));");
    }
}
