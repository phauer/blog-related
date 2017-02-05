package de.philipphauer.blog.resources.dto;

import com.google.common.base.MoreObjects;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.UUID;

@ApiModel("Payload for band retrieval")
public class BandRetrievalDTO {
    @ApiModelProperty(value = "UUID", required = true)
    private UUID id;
    @ApiModelProperty(value = "Name of the band", required = true)
    private String name;
    @ApiModelProperty(value = "Year of the foundation", required = true)
    private int foundation;

    public BandRetrievalDTO setId(UUID id) {
        this.id = id;
        return this;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BandRetrievalDTO setName(String name) {
        this.name = name;
        return this;
    }

    public int getFoundation() {
        return foundation;
    }

    public BandRetrievalDTO setFoundation(int foundation) {
        this.foundation = foundation;
        return this;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("foundation", foundation)
                .toString();
    }
}
