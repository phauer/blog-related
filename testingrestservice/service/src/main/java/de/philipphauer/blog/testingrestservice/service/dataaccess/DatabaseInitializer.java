package de.philipphauer.blog.testingrestservice.service.dataaccess;

import de.philipphauer.blog.testingrestservice.service.dataaccess.entities.BlogEntity;
import de.philipphauer.blog.testingrestservice.service.dataaccess.entities.CommentEntity;
import de.philipphauer.blog.testingrestservice.service.dataaccess.entities.PostEntity;
import de.svenjacobs.loremipsum.LoremIpsum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DatabaseInitializer.class);
    LoremIpsum loremIpsum = new LoremIpsum();

    @Autowired
    private BlogRepository repo;

    @Override
    public void run(String... strings) throws Exception {
        log.info("Creating dummy data.");

        List<BlogEntity> blogs = createBlogs(6);
        repo.save(blogs);
    }

    private List<CommentEntity> createComments(int amount) {
        return Stream.generate(() -> new CommentEntity()
                .setAuthor(createRandomName())
                .setCreatedDateTime(LocalDateTime.now())
                .setContent(loremIpsum.getParagraphs())
        )
                .limit(amount)
                .collect(Collectors.toList());
    }

    private List<PostEntity> createPosts(int amount) {
        return Stream.generate(() -> new PostEntity()
                .setAuthor(createRandomName())
                .setCreatedDateTime(LocalDateTime.now())
                .setTeaser(loremIpsum.getParagraphs(1))
                .setContent(loremIpsum.getParagraphs(7))
                .setComments(createComments(5))
                .setTitle(loremIpsum.getWords(7))
        )
                .limit(amount)
                .collect(Collectors.toList());
    }

    private List<BlogEntity> createBlogs(int amount) {
        return Stream.generate(() -> new BlogEntity()
                .setName(loremIpsum.getWords(5))
                .setDescription(loremIpsum.getParagraphs(1))
                .setPosts(createPosts(9))
                .setUrl("http://www." + loremIpsum.getWords(1, 10) + ".com")
        )
                .limit(amount)
                .collect(Collectors.toList());
    }

    private List<String> firstNames = Arrays.asList("Max", "Paul", "Tim", "Nils", "Angela", "Maria", "Lea", "Sven", "Helena");
    private List<String> lastNames = Arrays.asList("Müller", "Schmidt", "Merkel", "Henkel", "Lange", "Marx", "Heine", "Fischer", "Bauer");

    private String createRandomName() {
        Random random = new Random();
        String firstName = firstNames.get(random.nextInt(firstNames.size()));
        String lastName = lastNames.get(random.nextInt(lastNames.size()));
        return firstName + " " + lastName;
    }
}
