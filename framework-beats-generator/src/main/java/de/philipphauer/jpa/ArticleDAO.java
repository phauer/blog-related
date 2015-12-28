package de.philipphauer.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.Collection;

public class ArticleDAO {

    private final EntityManager entityManager;

    public ArticleDAO() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myPersistenceUnit");
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    public void save(Article article) {
        entityManager.getTransaction().begin();
        entityManager.persist(article);
        entityManager.getTransaction().commit();
    }

    public Collection<Article> findAll() {
        Query query = entityManager.createQuery("SELECT e FROM Article e");
        return (Collection<Article>) query.getResultList();
    }

    public void close(){
        entityManager.close();
    }
}
