package de.philipphauer.blog.testingrestservice.service.rest.dto;

public class ReferenceDTO {

    private long id;
    private String name;
    private String href;

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

    public String getHref() {
        return href;
    }

    public ReferenceDTO setHref(String href) {
        this.href = href;
        return this;
    }
}
