package kotlinVariant

import java.time.Instant

//Data classes: Each entity definition in a single line! We get immutability, constructor, hashCode(), equals(), toString() for free.
class BlogEntity(val id: Long, val name: String, val posts: List<PostEntity>?)
class PostEntity(val id: Long, val date: Instant?, val author: AuthorEntity?, val text: String, comments: List<CommentEntity>?)
class AuthorEntity(val name: String, val email: String?)
class CommentEntity(val text: String, val author: AuthorEntity, date: Instant)
//"val" makes the beans immutable ("var" fields can be modified).
//The type "String" can never be null. The compiler enforces this!
//Contrarily, the type "String?" is nullable. Null-safe types make the code much safer and avoid bloating null checks.

class BlogDTO(val id: Long, val name: String, val posts: List<PostDTO>?)
class PostDTO(val id: Long, val author: String, val date: String?, val text: String, commentsHref: String)

//Usage of "single expression function": No body {} is necessary, if there is only a single expression.
//Less boilerplate with the collection API : we can call map() directly on a list and it returns a list.
//Implicit variable "it" (= parameter) makes lambda syntax even shorter. but you can also write "para -> mapToBlogDTO(para)".
fun mapToBlogDTOs(entities: List<BlogEntity>) = entities.map { mapToBlogDTO(it) }
fun mapToBlogDTO(entity: BlogEntity) = BlogDTO(
        id = entity.id,
        name = entity.name,
        posts = entity.posts?.map { mapToPostDTO(it) }
        //The Kotlin compiler forces me to consider that posts can be null.
        //We can't call map() directly on posts, because it can be null.
        //The null-safe call ("?.") invokes the operation only if posts are not null. Otherwise the whole expression is null.
)
fun mapToPostDTO(entity: PostEntity) = PostDTO(
        //easy to read due to named arguments.
        id = entity.id,
        date = entity.date?.epochSecond.toString(), //"?." (null safe call). if date is null, null is assigned. Otherwise the epochSecond is retrieved and assigned.
        author = entity.author?.name ?: "Anonymous", //The elvis operator ("?:") makes Java's getNameOrDefault() a one-liner! If left side of "?:" is null, the right side is returned. Otherwise the left side is returned.
        text = entity.text,
        commentsHref = "posts/${entity.id}/comments" //String interpolation!
)


//warp up: kotlin code is...
// a) extremely concise (data classes, single expression function, field accessor, compact list operations, null-safe calls, elvis operator),
//lines of code: 201 lines (java) vs 18 lines (kotlin)! (and java doesn't include hashCode(), toString(), hashCode())
//=> factor 11+ when it comes to code for structs and mapping!! no boilerplate in Kotlin.
// b) more readable (named arguments) and
// c) less error-prone (compiler enforced null safety, immutability, no manually written toString(), hashCode(), equals()).
// extremely reduced boilerplate.
