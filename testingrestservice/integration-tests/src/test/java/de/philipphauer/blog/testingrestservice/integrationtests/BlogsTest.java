package de.philipphauer.blog.testingrestservice.integrationtests;

import com.jayway.awaitility.Duration;
import com.jayway.awaitility.core.ConditionFactory;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.filter.log.RequestLoggingFilter;
import com.jayway.restassured.filter.log.ResponseLoggingFilter;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.specification.RequestSpecification;
import de.philipphauer.blog.testingrestservice.integrationtests.dto.BlogDTO;
import de.philipphauer.blog.testingrestservice.integrationtests.dto.BlogListDTO;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static com.jayway.awaitility.Awaitility.await;
import static com.jayway.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

public class BlogsTest {

    //=> create test code that is readable and easy write. this way keep tests maintainable.


    //use rest-assured (nice readable fluent api)
    @Test
    public void collectionResourceOK(){
        given()
                .param("limit", 20)
                .when()
                .get("blogs")
                .then()
                .statusCode(200);
        //(import static com.jayway.restassured.RestAssured.given;)
    }

    String host = System.getProperty("host");//better remove this lines for clearity
    String port = System.getProperty("port");

    //use spec to reuse common request parameter
    private static RequestSpecification spec;

    @BeforeClass
    public static void initSpec(){
        spec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri("http://localhost:8080/")
                .addFilter(new ResponseLoggingFilter())//log request and response for better debugging. You can also only log if a requests fails.
                .addFilter(new RequestLoggingFilter())
                .build();
    }
    @Test
    public void useSpec(){
        given()
                .spec(spec)
                .param("limit", 20)
                .when()
                .get("blogs")
                .then()
                .statusCode(200);
    }

    @Test
    public void createBlogAndCheckExistence(){
        //use POJOs/DTO to create dummy data and use serialization to get JSON. don't construct json manually.
        //construction your json payload with javax.json.JsonObject doesn't make any fun at all. verbose, cumbersome, not type-safe and error-prone. instead use POJOs.

        //POJOs:
        //DON'T use ordinary setters -> verbose to write.
        //<example code>
        //DON'T use huge constructor with every possible field as an argument -> hard to read
        BlogDTO blogDTO = new BlogDTO("Example", "Example", "www.blogdomain.de");//which parameter means what? hard to read.
        //better: use fluent setter in POJO -> readable! see meaning of every argument.
        //use intelliJ's generator for setter (Alt+Insert > Getter and Setter) can be performed using only the keyboard.
        //<POJO code>
        BlogDTO newBlog = new BlogDTO()
                .setName("Example")
                .setDescription("Example")
                .setUrl("www.blogdomain.de");
        //object mapping is built-in into rest-assured. only pass pojo to post. automatically serialized to json and put into the http body
        //just add jackson as a project dependency -> rest-assured will automatically use it.
        String locationHeader = given()
                .spec(spec)
                .body(newBlog)
                .when()
                .post("blogs")
                .then()
                .statusCode(201)
                .extract().header("location");
        //extract location header and check existence
        //use POJO and deserialize respone json to your POJO again. use @JsonIgnoreProperties(ignoreUnknown = true) in pojo if necessary.
        BlogDTO retrievedBlog = given()
                .spec(spec)
                .when()
                .get(locationHeader)
                .then()
                .statusCode(200)
                .extract().as(BlogDTO.class);
        //using you POJOs (instead of JSONObject or Strings) makes equality check easy, typesafe and readeble.
        //use assertj (nice fluent, powerful and typesafe testing api; produces readable failure messages). I like it much more than hamcrest (problem to find matcher that can be used with type XY).
        assertThat(retrievedBlog.getName()).isEqualTo(newBlog.getName());
        assertThat(retrievedBlog.getDescription()).isEqualTo(newBlog.getDescription());
        assertThat(retrievedBlog.getUrl()).isEqualTo(newBlog.getUrl());
        //import static org.assertj.core.api.Assertions.assertThat;
    }

    //write clean test code: keep your test readable and maintainable! keep test methods short; use sub methods with nice descriptive names to make your test readable
    //every time you start building block and override it with a comment -> extract the block to a method instead and use the comment as a method name. (Strg+Alt+M in IntelliJ)
    @Test
    public void createBlogAndCheckExistenceReadable(){
        BlogDTO newBlog = createDummyBlog();
        String blogResourceLocation = createResource("blogs", newBlog);
        BlogDTO retrievedBlog = getResource(blogResourceLocation, BlogDTO.class);
        assertEqualBlog(newBlog, retrievedBlog);
    }

    private BlogDTO createDummyBlog() {
        return new BlogDTO()
                .setName("Example Name")
                .setDescription("Example Description")
                .setUrl("www.blogdomain.de");
    }

    //nice reusable method
    private String createResource(String path, Object bodyPayload) {
        return given()
                .spec(spec)
                .body(bodyPayload)
                .when()
                .post(path)
                .then()
                .statusCode(201)
                .extract().header("location");
    }

    //nice reusable method
    private <T> T getResource(String locationHeader, Class<T> responseClass) {
        return given()
                    .spec(spec)
                    .when()
                    .get(locationHeader)
                    .then()
                    .statusCode(200)
                    .extract().as(responseClass);
    }

    private void assertEqualBlog(BlogDTO newBlog, BlogDTO retrievedBlog) {
        assertThat(retrievedBlog.getName()).isEqualTo(newBlog.getName());
        assertThat(retrievedBlog.getDescription()).isEqualTo(newBlog.getDescription());
        assertThat(retrievedBlog.getUrl()).isEqualTo(newBlog.getUrl());
    }

    //use abstract test class (spec, generic methods (createResource() and getResource())

    //use asserj's as() to add domain information to your assertion failure messages
    private void assertEqualBlog2(BlogDTO newBlog, BlogDTO retrievedBlog) {
        assertThat(retrievedBlog.getName()).as("Blog Name").isEqualTo(newBlog.getName());
        assertThat(retrievedBlog.getDescription()).as("Blog Description").isEqualTo(newBlog.getDescription());
        assertThat(retrievedBlog.getUrl()).as("Blog URL").isEqualTo(newBlog.getUrl());
    }

    //I always create a POJO and map json to object. even when used just once for a request response.
    //to simplify the creation of the POJO class, a) consider public fields (OMG!) b) only add the fields you are interested in (+@JsonIgnoreProperties(ignoreUnknown = true))
    //<code BlogListDTO> vgl with json-response (with all fields)
    //but: when you use class to create a dummy obj (e.g. to post it to the service) --> easier with fluent setter and private fields.
    @Test
    public void getAllBlogsWithMapping(){
        BlogListDTO retrievedBlogs = given()
                .spec(spec)
                .when()
                .get("blogs")
                .then()
                .statusCode(200)
                .extract().as(BlogListDTO.class);
        assertThat(retrievedBlogs.count).isGreaterThan(7);
        assertThat(retrievedBlogs.blogs).isNotEmpty();
    }

    //alternative to separate POJO class/Mapping:
    // to extract simple things from response -> jsonpath
    // (strings -> but not type-safe and error-prone). only for simple things.
    @Test
    public void getAllBlogsWithJsonPath(){
        //A) using assertj (i prefer because easy to debug and readable; and typesafe, finding matcher)
        JsonPath retrievedBlogs = given()
                .spec(spec)
                .when()
                .get("blogs")
                .then()
                .statusCode(200)
                .extract().jsonPath();
        assertThat(retrievedBlogs.getInt("count")).isGreaterThan(5);
        assertThat(retrievedBlogs.getList("blogs.id")).isNotEmpty();

        //B) using directly in rest-assured statement with hamcrest matcher. built-in hamcrest support in rest-assured. shorter&concise, you have to use hamcrest. ;-)
        //not type-safe, error-prone, harder to debug
        //import static org.hamcrest.Matchers.*;
        given()
                .spec(spec)
                .when()
                .get("blogs")
                .then()
                .statusCode(200)
                .content("count", greaterThan(5))
                .content("blogs", is(not(empty())));
    }

    //more sophisticated assertions
    @Test
    public void createBlogAndCheckInList(){
        BlogDTO newBlog = createDummyBlog();
        String blogResourceLocation  = createResource("blogs", newBlog);
        int createdBlogId = extractId(blogResourceLocation);

        //use assertj to make powerful assertions about the responded data (e.g. list containsId)
        //a) object mapping + assertj. typesafe and readable, but more verbose. easier to debug.
        BlogListDTO retrievedBlogList = given()
                .spec(spec)
                .when()
                .get("blogs")
                .then()
                .statusCode(200)
                .extract().as(BlogListDTO.class);
        assertThat(retrievedBlogList.blogs)
                .extracting(blogEntry -> blogEntry.id)
                .contains(createdBlogId);
        //nice extracting() (like map() from java 8 stream api)

        //b) jsonpath + hamcrest. jsonpath is also powerful
        // (recognizes that "blogs" is a field. "blogs.id" returns list of ids.
        // less robust, harder to debug, but more concise. but trouble with types.
        given()
                .spec(spec)
                .when()
                .get("blogs")
                .then()
                .statusCode(200)
                .content("blogs.id", hasItem(createdBlogId));
        //however, jsonpath can be useful -> see docs for more details

        //c) jsonpath + assertj
        JsonPath retrievedBlogList2 = given()
                .spec(spec)
                .when()
                .get("blogs")
                .then()
                .statusCode(200)
                .extract().jsonPath();
        assertThat(retrievedBlogList2.getList("blogs.id"))
                .contains(createdBlogId);
    }

    @Test
    public void jsonPath(){
        JsonPath jsonPath = new JsonPath("{\"blogs\":[\"posts\":[{\"author\":{\"name\":\"Paul\"}}]]}");
        //jsonpath useful when accessing one element in a deeply nested document. don't write a pojo class if you only interested in one element
        jsonPath.getString("blogs[0].posts[0].author.name");
    }

    private int extractId(String resourceLocation) {
        int slashIndex = resourceLocation.lastIndexOf("/");
        String idString = resourceLocation.substring(slashIndex + 1);
        return Integer.parseInt(idString);
    }

    //Wait and Poll: Dealing with asynchronous behavior (like events)
    //use awaitility to wait and poll until a certain assertion becomes true or a timeout exceeds.
    //import static com.jayway.awaitility.Awaitility.await;
    @Test
    public void waitAndPoll(){
        sendAsyncEventThatChangesABlog(123);
        await().atMost(Duration.TWO_SECONDS).until(() -> {
            given()
                    .when()
                    .get("blogs/123")
                    .then()
                    .statusCode(200);
        });
    }

    private void sendAsyncEventThatChangesABlog(int i) {
    }

    //await(), atMost() etc returns immutable ConditionFactory. -> configure once behavior for polling and waiting and reuse it
    public static final ConditionFactory WAIT = await()
            .atMost(new Duration(15, TimeUnit.SECONDS))
            .pollInterval(Duration.ONE_SECOND)
            .pollDelay(Duration.ONE_SECOND);
    @Test
    public void waitAndPoll2(){
        WAIT.until(() -> {
            //...
        });
    }


        //    - Given-When-Then pattern
    //    - keep this parts short. best: only one or a few parameterized method invocation. use submethods.
    @Test
    public void test(){
        //Given: set up the input for the action under test (test data, mocks, stubs)
        //When: execute the action you want to test.
        //Then: check the output with assertions
    }
}
