package de.philipphauer.mongojack;

import org.junit.Test;

public class ProductDAOTest {

    @Test
    public void save() throws Exception {
        ProductDAO dao = new ProductDAO();
        dao.save(new Product("Lego Car", 100));
    }
}