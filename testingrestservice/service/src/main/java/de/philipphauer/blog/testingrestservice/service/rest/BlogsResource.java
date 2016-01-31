package de.philipphauer.blog.testingrestservice.service.rest;

import de.philipphauer.blog.testingrestservice.service.dataaccess.entities.BlogEntity;
import de.philipphauer.blog.testingrestservice.service.dataaccess.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogsResource {

    @Autowired
    private BlogRepository repository;

    //@RequestParam(value="name", defaultValue="World") String nam

    //you should really never ever pass your DAO entity through your rest service, but this is just rapid prototyping.

    @RequestMapping("/blogs")
    public Iterable<BlogEntity> getAll() {
        Iterable<BlogEntity> blogs = repository.findAll();
        return blogs;
    }
}
