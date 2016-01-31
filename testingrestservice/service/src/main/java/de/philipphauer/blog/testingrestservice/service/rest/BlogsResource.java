package de.philipphauer.blog.testingrestservice.service.rest;

import de.philipphauer.blog.testingrestservice.service.dataaccess.BlogRepository;
import de.philipphauer.blog.testingrestservice.service.dataaccess.PostRepository;
import de.philipphauer.blog.testingrestservice.service.dataaccess.entities.BlogEntity;
import de.philipphauer.blog.testingrestservice.service.dataaccess.entities.PostEntity;
import de.philipphauer.blog.testingrestservice.service.rest.dto.BlogDTO;
import de.philipphauer.blog.testingrestservice.service.rest.dto.BlogsDTO;
import de.philipphauer.blog.testingrestservice.service.rest.dto.ReferenceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/blogs", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
public class BlogsResource {

    @Autowired
    private BlogRepository blogRepo;
    @Autowired
    private PostRepository postRepo;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public BlogsDTO getAll() {
        Collection<BlogEntity> blogs = (Collection<BlogEntity>) blogRepo.findAll();
        BlogsDTO blogList = mapToDTO(blogs);
        return blogList;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public BlogDTO getBlog(@PathVariable("id") Long id) {
        BlogEntity blog = blogRepo.findOne(id);
        BlogDTO blogDTO = mapToDTO(blog);
        return blogDTO;
    }

    @RequestMapping(value = "/{blogId}/posts", method = RequestMethod.GET)
    public List<ReferenceDTO> getAllBlogPosts(@PathVariable("blogId") Long blogId) {
        BlogEntity blog = blogRepo.findOne(blogId);
        List<PostEntity> posts = blog.getPosts();
        return mapToDTO(blog.getId(), posts);
    }

    //let's ignore the blogId for the sake of simplicity, because the postId is unique and and not context is necessary
    @RequestMapping(value = "/{blogId}/posts/{postId}", method = RequestMethod.GET)
    public PostEntity getBlogPost(@PathVariable("blogId") Long blogId, @PathVariable("postId") Long postId) {
        PostEntity post = postRepo.findOne(postId);
        return post;
    }

    private BlogDTO mapToDTO(BlogEntity blog) {
        List<PostEntity> posts = blog.getPosts();
        List<ReferenceDTO> postsDTO = mapToDTO(blog.getId(), posts);
        BlogDTO blogDTO = new BlogDTO()
                .setName(blog.getName())
                .setDescription(blog.getDescription())
                .setPosts(postsDTO)
                .setUrl(blog.getUrl());
        return blogDTO;
    }

    private List<ReferenceDTO> mapToDTO(long blogId, List<PostEntity> posts) {
        return posts.stream()
                    .map(post -> mapToDTO(blogId, post))
                    .collect(Collectors.toList());
    }

    private ReferenceDTO mapToDTO(long blogId, PostEntity post){
        long id = post.getId();
        String title = post.getTitle();
        String url = "/blogs/" + blogId + "/posts/" + id;
        ReferenceDTO ref = new ReferenceDTO().setId(id).setName(title).setUrl(url);
        return ref;
    }

    private BlogsDTO mapToDTO(Collection<BlogEntity> blogs) {
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
