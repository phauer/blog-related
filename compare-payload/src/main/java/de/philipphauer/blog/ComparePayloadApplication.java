package de.philipphauer.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

@SpringBootApplication
@RestController
public class ComparePayloadApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComparePayloadApplication.class, args);
	}

	@RequestMapping(value = "/blogposts", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public List<BlogPost> getBlogPosts() {
        ArrayList<BlogPost> blogPosts = new ArrayList<>();
        blogPosts.add(new BlogPost().setAuthor("Philipp").setContent("Super Post").setCreated(Instant.now()));
        blogPosts.add(new BlogPost().setAuthor("Peter").setContent("Nice Post").setCreated(Instant.now().minusSeconds(60)));
        blogPosts.add(new BlogPost().setAuthor("Albert").setContent("Great Post").setCreated(Instant.now().minusSeconds(60*60)));
        return blogPosts;
	}

    @RequestMapping(value = "/blogposts2", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<BlogPost2> getBlogPosts2() {
        ArrayList<BlogPost2> blogPosts = new ArrayList<>();
        blogPosts.add(new BlogPost2().setAuthorName("Philipp").setContent("Super Post").setCreated(format(Instant.now())));
        blogPosts.add(new BlogPost2().setAuthorName("Peter").setContent("Nice Post").setCreated(format(Instant.now().minusSeconds(60))));
        blogPosts.add(new BlogPost2().setAuthorName("Albert").setContent("Great Post").setCreated(format(Instant.now().minusSeconds(60*60))));
        return blogPosts;
    }

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    private String format(Instant now) {
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(now, ZoneOffset.UTC);
        return FORMATTER.format(zonedDateTime);
    }
}
