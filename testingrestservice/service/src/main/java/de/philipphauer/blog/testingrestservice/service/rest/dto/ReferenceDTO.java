package de.philipphauer.blog.testingrestservice.service.rest.dto;

public class ReferenceDTO {

    private long id;
    private String name;
    private String url;

    public String getName() {
        return name;
    }

    public ReferenceDTO setName(String name) {
        this.name = name;
        return this;
    }

    public long getId() {
        return id;
    }

    public ReferenceDTO setId(long id) {
        this.id = id;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public ReferenceDTO setUrl(String url) {
        this.url = url;
        return this;
    }
}
