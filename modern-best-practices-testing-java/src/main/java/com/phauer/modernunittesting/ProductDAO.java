package com.phauer.modernunittesting;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class ProductDAO {

    private final JdbcTemplate template;

    public ProductDAO(JdbcTemplate template) {
        this.template = template;
    }

    public List<ProductEntity> findProducts() {
        return template.query("select * from products;", this::map);
    }

    private ProductEntity map(ResultSet resultSet, int i) throws SQLException {
        return new ProductEntity()
                .setId(resultSet.getString("id"))
                .setName(resultSet.getString("name"));
    }

}


