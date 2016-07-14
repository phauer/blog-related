package de.philipphauer.blog;

import de.philipphauer.blog.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping(value = "/products")
public class ProductsResource {

    @Autowired
    private EntityManager entityManager;

    @Transactional
    @RequestMapping(value = "", method = RequestMethod.POST)
    public void createProduct(@RequestBody Map requestBody) {
        String name = (String) requestBody.get("name");
        Product paul = new Product().setName(name);
        entityManager.persist(paul);
    }

    @Transactional
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Collection<Product> getProducts() {
        Query query = entityManager.createQuery("SELECT p FROM Product p");
        return (Collection<Product>) query.getResultList();
    }
}