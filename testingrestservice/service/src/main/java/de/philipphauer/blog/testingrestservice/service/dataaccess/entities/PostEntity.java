package de.philipphauer.blog.testingrestservice.service.dataaccess.entities;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class PostEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private LocalDateTime createdDateTime;
    private String title;
    private String author;
    private String slug;
    private int viewCount;
    private String featuredImage;
    private String[] tags;
    @Size(max=300)
    private String teaser;
    @Lob
    private String content;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CommentEntity> comments;

    public String[] getTags() {
        return tags;
    }

    public PostEntity setTags(String[] tags) {
        this.tags = tags;
        return this;
    }

    public String getFeaturedImage() {
        return featuredImage;
    }

    public PostEntity setFeaturedImage(String featuredImage) {
        this.featuredImage = featuredImage;
        return this;
    }

    public int getViewCount() {
        return viewCount;
    }

    public PostEntity setViewCount(int viewCount) {
        this.viewCount = viewCount;
        return this;
    }

    public String getSlug() {
        return slug;
    }

    public PostEntity setSlug(String slug) {
        this.slug = slug;
        return this;
    }

    public long getId() {
        return id;
    }

    public PostEntity setId(long id) {
        this.id = id;
        return this;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public PostEntity setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public PostEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getTeaser() {
        return teaser;
    }

    public PostEntity setTeaser(String teaser) {
        this.teaser = teaser;
        return this;
    }

    public String getContent() {
        return content;
    }

    public PostEntity setContent(String content) {
        this.content = content;
        return this;
    }

    public List<CommentEntity> getComments() {
        return comments;
    }

    public PostEntity setComments(List<CommentEntity> comments) {
        this.comments = comments;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public PostEntity setAuthor(String author) {
        this.author = author;
        return this;
    }
}
