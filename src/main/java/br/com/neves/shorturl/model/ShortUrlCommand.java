package br.com.neves.shorturl.model;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;

public class ShortUrlCommand {
// ------------------------------ FIELDS ------------------------------

    private static final String EXAMPLE_URL = "https://www.google.com/search?q=github&rlz=1C1GCEA_enBR852BR852&sxsrf=ALeKk03cy1cOFcQ8eiLvioFgbO4OESDT7Q:1623314648540&source=lnms&tbm=isch&sa=X&ved=2ahUKEwjghPCn1ozxAhU7JrkGHRblCmwQ_AUoAXoECAEQAw&biw=1536&bih=754";

    @NotBlank(message = "The originalUrl is required")
    @URL(message = "The url is not valid")
    @ApiModelProperty(
            name = "originalUrl",
            dataType = "String",
            value = "The url to be shortened",
            example = EXAMPLE_URL,
            required = true)
    private String originalUrl;

    @NotBlank(message = "The loginName is required")
    @ApiModelProperty(
            name = "loginName",
            dataType = "String",
            value = "The login name",
            example = "nevesg",
            required = true)
    private String loginName;

    @ApiModelProperty(
            name = "expirationDays",
            dataType = "Integer",
            value = "The number of days the hash will expire",
            example = "1")
    private Integer expirationDays;

// --------------------- GETTER / SETTER METHODS ---------------------

    public Integer getExpirationDays() {
        return expirationDays;
    }

    public void setExpirationDays(Integer expirationDays) {
        this.expirationDays = expirationDays;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }
}
