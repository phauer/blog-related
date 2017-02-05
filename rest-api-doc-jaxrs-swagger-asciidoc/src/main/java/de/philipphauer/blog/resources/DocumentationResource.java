package de.philipphauer.blog.resources;

import com.google.common.base.Throwables;
import com.google.common.io.Resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Path("/")
public class DocumentationResource {

    @GET
    @Path("swagger.json")
    @Produces(MediaType.APPLICATION_JSON)
    public String swaggerJson() {
        return getFileContent("swagger.json");
    }

    @GET
    @Path("swagger.yaml")
    @Produces("application/yaml")
    public String swaggerYaml(){
        return getFileContent("swagger.yaml");
    }

    @GET
    @Path("application-doc.html")
    @Produces(MediaType.TEXT_HTML)
    public String doc(){
        return getFileContent("index.html");
    }

    private String getFileContent(String fileName) {
        try {
            URL url = Resources.getResource(fileName);
            return Resources.toString(url, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }
}
