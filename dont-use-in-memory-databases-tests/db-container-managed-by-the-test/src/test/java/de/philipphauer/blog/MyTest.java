package de.philipphauer.blog;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.testcontainers.containers.MySQLContainer;

import javax.sql.DataSource;

public class MyTest {

    private static DataSource dataSource;
    private static MySQLContainer mysql;

    @BeforeClass
    public static void init() throws InterruptedException{
        //You can also use the GenericContainer for arbitrary containers
        //But there are convenient classes for common databases.
        mysql = new MySQLContainer("mysql:5.5.53");
        mysql.start();
        dataSource = DataSourceBuilder.create()
                .url(mysql.getJdbcUrl())
                .username(mysql.getUsername())
                .password(mysql.getPassword())
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();
    }

    @AfterClass
    public static void destroy(){
        mysql.close();
    }

    @Test
    public void foo(){
        DataSourceTransactionManager txManager = new DataSourceTransactionManager(dataSource);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        TransactionStatus transaction = txManager.getTransaction(new DefaultTransactionDefinition());
        jdbcTemplate.execute("CREATE TABLE BAR (" +
                "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                "name varchar(200)" +
                ");");
        txManager.commit(transaction);
    }
}
