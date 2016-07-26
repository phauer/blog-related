package de.philipphauer.blog;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class BlogPost {

    private String author;
    private String content;
    private Instant created;

    public String getAuthor() {
        return author;
    }

    public BlogPost setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getContent() {
        return content;
    }

    public BlogPost setContent(String content) {
        this.content = content;
        return this;
    }

    public Instant getCreated() {
        return created;
    }

    public BlogPost setCreated(Instant created) {
        this.created = created;
        return this;
    }
}
