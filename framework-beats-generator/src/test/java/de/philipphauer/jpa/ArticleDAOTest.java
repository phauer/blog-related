package de.philipphauer.jpa;

import de.philipphauer.h2.H2WebConsole;
import org.junit.Test;

public class ArticleDAOTest {

    @Test
    public void saveAndLoad() throws Exception {
        ArticleDAO dao = new ArticleDAO();
        Article article = new Article("Car");
        dao.save(article);

//        Collection<Article> articles = dao.findAll();
//        Assertions.assertThat(articles).contains(article);

        System.out.println("Now you can take a look at the h2 web console...");
        H2WebConsole.start();
        Thread.sleep(40000);

    }
}