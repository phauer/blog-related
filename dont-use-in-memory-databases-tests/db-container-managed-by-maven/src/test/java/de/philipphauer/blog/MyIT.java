package de.philipphauer.blog;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;

public class MyIT {

    private static DataSource dataSource;

    @BeforeClass
    public static void init() throws InterruptedException{
        // the maven-docker-plugin already waits. no need to do it here.
        String port = System.getProperty("mysql.port", "3306");
        String pw = System.getProperty("mysql.pw", "root");
        String url = "jdbc:mysql://localhost:" + port + "?autoReconnect=true";
        System.out.println("MySQL URL: " + url +" with pw " + pw);
        dataSource = DataSourceBuilder.create()
                .url(url)
                .username("root")
                .password(pw)
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();
    }

    @Test
    public void foo(){
        DataSourceTransactionManager txManager = new DataSourceTransactionManager(dataSource);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        TransactionStatus transaction = txManager.getTransaction(new DefaultTransactionDefinition());
        jdbcTemplate.execute("DROP SCHEMA IF EXISTS testdb");
        jdbcTemplate.execute("CREATE SCHEMA testdb");
        jdbcTemplate.execute("CREATE TABLE testdb.BAR (" +
                "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                "name varchar(200)" +
                ");");
        txManager.commit(transaction);
    }
}
