package de.philipphauer.blog;

import de.philipphauer.blog.resources.BandResource;
import de.philipphauer.blog.resources.DocumentationResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class RestApiDocApplication extends Application<RestApiDocConfiguration> {

    public static void main(final String[] args) throws Exception {
        new RestApiDocApplication().run(args);
    }

    @Override
    public String getName() {
        return "RestApiDocApplication";
    }

    @Override
    public void initialize(final Bootstrap<RestApiDocConfiguration> bootstrap) {
    }

    @Override
    public void run(final RestApiDocConfiguration configuration,
                    final Environment environment) {
        environment.jersey().register(BandResource.class);
        environment.jersey().register(DocumentationResource.class);
    }

}
