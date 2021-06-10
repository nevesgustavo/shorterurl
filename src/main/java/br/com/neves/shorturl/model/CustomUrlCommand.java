package br.com.neves.shorturl.model;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

public class CustomUrlCommand extends ShortUrlCommand {
// ------------------------------ FIELDS ------------------------------

    @NotBlank(message = "The hash is required")
    @ApiModelProperty(
            name = "hash",
            dataType = "String",
            value = "Your custom hash",
            example = "YaXmxi",
            required = true)
    private String hash;

// --------------------- GETTER / SETTER METHODS ---------------------

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
