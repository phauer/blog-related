package de.philipphauer.blog.resources.dto;

import com.google.common.base.MoreObjects;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Payload for band creation")
public class BandCreationDTO {
    @ApiModelProperty(value = "Name of the band", required = true)
    private String name;
    @ApiModelProperty(value = "Year of the foundation", required = true)
    private int foundation;

    public String getName() {
        return name;
    }

    public BandCreationDTO setName(String name) {
        this.name = name;
        return this;
    }

    public int getFoundation() {
        return foundation;
    }

    public BandCreationDTO setFoundation(int foundation) {
        this.foundation = foundation;
        return this;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("foundation", foundation)
                .toString();
    }
}
