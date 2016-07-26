package de.philipphauer.blog;

import javax.xml.bind.annotation.XmlRootElement;

public class BlogPost2 {

    private String authorName;
    private String created;
    private String content;

    public String getAuthorName() {
        return authorName;
    }

    public BlogPost2 setAuthorName(String authorName) {
        this.authorName = authorName;
        return this;
    }

    public String getContent() {
        return content;
    }

    public BlogPost2 setContent(String content) {
        this.content = content;
        return this;
    }

    public String getCreated() {
        return created;
    }

    public BlogPost2 setCreated(String created) {
        this.created = created;
        return this;
    }
}
