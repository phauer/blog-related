package de.philipphauer.blog.testingrestservice.service.rest;

import de.philipphauer.blog.testingrestservice.service.dataaccess.BlogRepository;
import de.philipphauer.blog.testingrestservice.service.dataaccess.entities.BlogEntity;
import de.philipphauer.blog.testingrestservice.service.rest.dto.BlogsDTO;
import de.philipphauer.blog.testingrestservice.service.rest.dto.ReferenceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/blogs", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
public class BlogsResource {

    @Autowired
    private BlogRepository repository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public BlogsDTO getAll() {
        Collection<BlogEntity> blogs = (Collection<BlogEntity>) repository.findAll();
        BlogsDTO blogList = map(blogs);
        return blogList;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public BlogEntity getBlog(@PathVariable("id") Long id) {
        BlogEntity blog = repository.findOne(id);
        return blog;
    }

    private BlogsDTO map(Collection<BlogEntity> blogs) {
        List<ReferenceDTO> blogRefs = blogs.stream()
                .map(blogEntry -> {
                    String name = blogEntry.getName();
                    long id = blogEntry.getId();
                    String url = "/blogs/" + id;
                    ReferenceDTO ref = new ReferenceDTO().setId(id).setName(name).setUrl(url);
                    return ref;
                })
                .collect(Collectors.toList());
        return new BlogsDTO().setBlogs(blogRefs);
    }
}
