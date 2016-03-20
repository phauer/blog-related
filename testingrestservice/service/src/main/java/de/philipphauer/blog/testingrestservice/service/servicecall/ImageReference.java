package de.philipphauer.blog.testingrestservice.service.servicecall;

public class ImageReference {

    private String id;
    private String href;

    public String getId() {
        return id;
    }

    public ImageReference setId(String id) {
        this.id = id;
        return this;
    }


    public String getHref() {
        return href;
    }

    public ImageReference setHref(String href) {
        this.href = href;
        return this;
    }
}
