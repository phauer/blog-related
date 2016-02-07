package de.philipphauer.blog.testingrestservice.service.rest.dto;

import java.util.List;

public class BlogDTO {

    private String name;
    private String description;
    private String url;
    private List<ReferenceDTO> posts;

    public String getUrl() {
        return url;
    }

    public BlogDTO setUrl(String url) {
        this.url = url;
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
