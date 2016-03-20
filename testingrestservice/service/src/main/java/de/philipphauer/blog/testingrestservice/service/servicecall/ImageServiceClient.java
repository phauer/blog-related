package de.philipphauer.blog.testingrestservice.service.servicecall;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

public class ImageServiceClient {

    private final String imageServiceHost;
    private final int imageServicePort;

    public ImageServiceClient(String imageServiceHost, int imageServicePort) {
        this.imageServiceHost = imageServiceHost;
        this.imageServicePort = imageServicePort;
    }

    public ImageReference requestImage(String id){
        RestTemplate restTemplate = new RestTemplate(Arrays.asList(new MappingJackson2HttpMessageConverter()));
        ImageReference imageReference = restTemplate.getForObject("http://{host}:{port}/images/{id}",
                ImageReference.class, imageServiceHost, imageServicePort, id);
        return imageReference;
    }
}
