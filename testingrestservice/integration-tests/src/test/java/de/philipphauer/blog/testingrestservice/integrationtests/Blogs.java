package de.philipphauer.blog.testingrestservice.integrationtests;

import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.filter.log.RequestLoggingFilter;
import com.jayway.restassured.filter.log.ResponseLoggingFilter;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.specification.RequestSpecification;
import de.philipphauer.blog.testingrestservice.integrationtests.dto.BlogDTO;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class Blogs {

    private static RequestSpecification spec;

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

    //use spec to reuse common request parameter
    @BeforeClass
    public static void initSpec(){
        spec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri("http://localhost:8080/")
                //log request and response for better debugging. You can also only log if a requests fails.
                .addFilter(new ResponseLoggingFilter())
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
        //use fluent setter in POJO
        //use intelliJ's generator for setter (Alt+Insert > Getter and Setter)
        //<POJO code>
        BlogDTO newBlog = new BlogDTO()
                .setName("Example Name")
                .setDescription("Example Description")
                .setUrl("www.blogdomain.de");
        //jackson is built-in into rest-assured. only pass pojo to post. automatically serialized to json and put into the http body
        //add jackson as project dependency

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
        assertThat(retrievedBlog.getName()).as("Blog Name").isEqualTo(newBlog.getName());
        assertThat(retrievedBlog.getDescription()).as("Blog Description").isEqualTo(newBlog.getDescription());
        assertThat(retrievedBlog.getUrl()).as("Blog URL").isEqualTo(newBlog.getUrl());
    }

    //clean code: keep your test method short; use sub methods with nice descriptive names to make your test readable
    @Test
    public void createBlogAndCheckExistenceReadable(){

    }


    //response:
    //- POJO even for reponse. If only for response -> consider public fields; use @IgnoreJsonProp
    //- to extract simple things from response -> jsonpath


    //use assertj
}
