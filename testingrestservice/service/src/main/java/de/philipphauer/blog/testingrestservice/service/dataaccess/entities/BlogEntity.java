package de.philipphauer.blog.testingrestservice.service.dataaccess.entities;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class BlogEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String name;
    @Size(max=300)
    private String description;
    private String url;
    @OneToMany(cascade = CascadeType.ALL)
    private List<PostEntity> posts;

    public long getId() {
        return id;
    }

    public BlogEntity setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public BlogEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public BlogEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<PostEntity> getPosts() {
        return posts;
    }

    public BlogEntity setPosts(List<PostEntity> posts) {
        this.posts = posts;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public BlogEntity setUrl(String url) {
        this.url = url;
        return this;
    }
}