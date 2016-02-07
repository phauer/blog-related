package de.philipphauer.blog.testingrestservice.service.rest.dto;

import java.util.List;

public class BlogsDTO {

    private int count;
    private int offset;
    private int limit;
    private List<ReferenceDTO> blogs;

    public List<ReferenceDTO> getBlogs() {
        return blogs;
    }

    public int getCount() {
        return count;
    }

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }

    public BlogsDTO setBlogs(List<ReferenceDTO> blogs) {
        this.blogs = blogs;
        return this;
    }

    public BlogsDTO setCount(int count) {
        this.count = count;
        return this;
    }

    public BlogsDTO setOffset(int offset) {
        this.offset = offset;
        return this;
    }

    public BlogsDTO setLimit(int limit) {
        this.limit = limit;
        return this;
    }
}
