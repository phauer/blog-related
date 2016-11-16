package kotlin

import java.time.Instant

//data classes: each entity definition in a single line! get immutability, constructor, hashCode(), equals(), toString() for free.
class BlogEntity(val id: Long, val name:String, val posts: List<PostEntity>?)
class PostEntity(val id: Long, val date: Instant?, val author: AuthorEntity?, val text: String, comments: List<CommentEntity>?)
class AuthorEntity(val name: String, val email: String?)
class CommentEntity(val text: String, val author: AuthorEntity, date: Instant)
//"val" makes the beans immutable ("var" fields can be modified)
//type "String" can never be null. compiler enforces this! "String?" is nullable. null-safe types make code much safer and avoid inflating null checks.

class BlogDTO(val id: Long, val name:String, val posts: List<PostDTO>?)
class PostDTO(val id: Long, val author: String, val date: String?, val text: String, commentsHref: String)

//usage of "single expression function". no body {} necessary, if there is only a single expression.
//less boilerplate for list operations: call map() directly on a list and it returns a list.
//implicit variable "it" (= parameter) makes lambda syntax even shorter. but also "para -> mapToBlogDTO(para)" possible
fun mapToBlogDTOs(entities: List<BlogEntity>) = entities.map { mapToBlogDTO(it) }

fun mapToBlogDTO(entity: BlogEntity) = BlogDTO(
        id = entity.id,
        name = entity.name,
        posts = entity.posts?.map { mapToPostDTO(it) } //kotlin compiler forces me to consider that posts can be null. we can't call map directly on posts, because it can be null. null-safe call "?." calls operation only of posts if not null. otherwise it returns null.
)
fun mapToPostDTO(entity: PostEntity) = PostDTO(
        //named arguments makes code very readable.
        id = entity.id,
        date = entity.date?.epochSecond.toString(), //"?." (null safe call). if date is null, null is assigned. otherwise the epochSecond is retrieved and assigned.
        author = entity.author?.name ?: "Anonymous", //elvis operator ("?:") makes Java' getNameOrDefault() a one-liner! if left side of "?:" is null, the right side is returned else the left side is returned.
        text = entity.text,
        commentsHref = "posts/${entity.id}/comments" //string interpolation!
)


//warp up: kotlin code is...
// a) extremely concise (data classes, single expression function, field accessor, compact list operations, null-safe calls, elvis operator),
//lines of code: 201 lines (java) vs 18 lines (kotlin). (and java doesn't include hashCode(), toString(), hashCode())
//factor 11+ when it comes to code for structs and mapping!
// b) more readable (named arguments) and
// c) less error-prone (compiler enforced null safety, immutability, no manually written toString(), hashCode(), equals()).
// extremely reduced boilerplate.
