package de.philipphauer.blog.testingrestservice.service.rest.dto;

import java.util.List;

public class BlogDTO {

    private String name;
    private String description;
    private String href;
    private List<ReferenceDTO> posts;

    public String getHref() {
        return href;
    }

    public BlogDTO setHref(String href) {
        this.href = href;
        return this;
    }

    public String getName() {
        return name;
    }

    public BlogDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public BlogDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<ReferenceDTO> getPosts() {
        return posts;
    }

    public BlogDTO setPosts(List<ReferenceDTO> posts) {
        this.posts = posts;
        return this;
    }
}
