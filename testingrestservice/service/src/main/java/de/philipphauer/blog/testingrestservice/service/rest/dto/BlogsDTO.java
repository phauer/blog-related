package de.philipphauer.blog.testingrestservice.service.rest.dto;

import java.util.List;

public class BlogsDTO {

    List<ReferenceDTO> blogs;

    public List<ReferenceDTO> getBlogs() {
        return blogs;
    }

    public BlogsDTO setBlogs(List<ReferenceDTO> blogs) {
        this.blogs = blogs;
        return this;
    }
}
