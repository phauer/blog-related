package de.philipphauer.blog.testingrestservice.integrationtests.dto;

import java.util.List;

public class BlogsDTO {

    public List<BlogReference> blogs;

    public static class BlogReference{
        public long id;
        public String name;
        public String href;
    }
}
