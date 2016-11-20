package javaVariant;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

//BlogEntity (received from persistence layer) map to BlogDTO (returned by our REST Service)
//Struct definition and mapping code are extremely verbose in java!
//Besides, null handling is cumbersome. Especially when it comes to nested objects that can be null.
//All values can be null. It's easy to run into NullPointerExceptions. This leads to error-prone code. And even if you add null-checks, it's easy to forget a check (because the compiler doesn't help you) and the code becomes very verbose.
//Argument lists are hard to read and error prone
class BlogEntity {
    private long id;
    private String name;
    private List<PostEntity> posts;

    //Praise my IDE for generating the constructor and getter boilerplate. Otherwise I would drive nuts.
    //Moreover, equals(), hashCode(), toString() are still missing!
    //AND you have to maintain these methods when field are added or removed.
    public BlogEntity(long id, String name, List<PostEntity> posts) {
        this.id = id;
        this.name = name;
        this.posts = posts;
    }

    public String getName() {
        return name;
    }

    public List<PostEntity> getPosts() {
        return posts;
    }

    public long getId() {
        return id;
    }
}

class PostEntity {
    private long id;
    private AuthorEntity author;
    private Instant date;
    private String text;
    private List<CommentEntity> comments;

    public PostEntity(long id, AuthorEntity author, Instant date, String text, List<CommentEntity> comments) {
        this.id = id;
        this.author = author;
        this.date = date;
        this.text = text;
        this.comments = comments;
    }

    public AuthorEntity getAuthor() {
        return author;
    }

    public Instant getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public List<CommentEntity> getComments() {
        return comments;
    }

    public long getId() {
        return id;
    }
}

class AuthorEntity {
    private String name;
    private String email;

    public AuthorEntity(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}

class CommentEntity {
    private String text;
    private AuthorEntity author;
    private Instant date;

    public CommentEntity(String text, AuthorEntity author, Instant date) {
        this.text = text;
        this.author = author;
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public AuthorEntity getAuthor() {
        return author;
    }

    public Instant getDate() {
        return date;
    }
}

class BlogDTO{
    private long id;
    private String name;
    private List<PostDTO> posts;

    public BlogDTO(long id, String name, List<PostDTO> posts) {
        this.id = id;
        this.name = name;
        this.posts = posts;
    }

    public String getName() {
        return name;
    }

    public List<PostDTO> getPosts() {
        return posts;
    }

    public long getId() {
        return id;
    }

}
class PostDTO{
    private long id;
    private String date;
    private String author;
    private String text;
    private String commentsHref;

    public PostDTO(long id, String date, String author, String text, String commentsHref) {
        this.id = id;
        this.date = date;
        this.author = author;
        this.text = text;
        this.commentsHref = commentsHref;
    }

    public String getAuthor() {
        return author;
    }

    public String getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public String getCommentsHref() {
        return commentsHref;
    }

    public long getId() {
        return id;
    }
}

class Mapper {
    public List<BlogDTO> mapToBlogDTOs(List<BlogEntity> entities){
        //verbose stream api
        return entities.stream()
                .map(this::mapToBlogDTO)
                .collect(Collectors.toList());
    }
    private BlogDTO mapToBlogDTO(BlogEntity entity){
        return new BlogDTO(
                entity.getId(),
                entity.getName(),
                mapToPostDTO(entity.getPosts())
        );
    }

    private List<PostDTO> mapToPostDTO(List<PostEntity> posts) {
        //what if posts is null?! very unsafe code!
        return posts.stream()
                .map(this::mapToPostDTO)
                .collect(Collectors.toList());
    }

    private PostDTO mapToPostDTO(PostEntity post) {
        //what if author, date, text or comments are null?! error-prone code!
        //easy to mess up parameter order (most of them are strings). hard to understand meaning of last parameter.
        return new PostDTO(
                post.getId(),
                post.getDate() != null ? post.getDate().getEpochSecond()+ "" : null, //hard to read. easy to forget null check.
                getNameOrDefault(post.getAuthor()),
                post.getText(),
                "posts/" + post.getId() + "/comments"
        );
    }

    //null checks bloat code. very verbose. hard to read.
    private String getNameOrDefault(AuthorEntity author) {
        if (author != null){
            String name = author.getName();
            if (name != null){
                return name;
            }
        }
        return "Anonymous";
    }
}
