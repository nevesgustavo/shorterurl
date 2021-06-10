package br.com.neves.shorturl.controller;

import br.com.neves.shorturl.endpoint.v1.UrlShorterController;
import br.com.neves.shorturl.model.CustomUrlCommand;
import br.com.neves.shorturl.model.ShortUrlCommand;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class UrlShorterControllerTest {

    private static final String ORIGINAL_URL = "http://localhost:8080/home";

    @Autowired
    private UrlShorterController urlShorterController;

    @Test
    void createCustomShortUrl() {
        ResponseEntity<String> customShortUrl = urlShorterController.createCustomShortUrl(createShortUrlCommand());
        Assertions.assertEquals(customShortUrl.getStatusCode(), HttpStatus.OK);
        Assertions.assertNotNull(customShortUrl.getBody());
        Assertions.assertTrue(customShortUrl.getBody().contains("123abc@"));
    }

    @Test
    void createShortUrl() {
        ResponseEntity<String> shortUrl = urlShorterController.createShortUrl(createShortUrlCommand());
        Assertions.assertEquals(shortUrl.getStatusCode(), HttpStatus.OK);
        Assertions.assertNotNull(shortUrl.getBody());
        Assertions.assertFalse(shortUrl.getBody().contains("123abc@"));
    }

    @Test
    void getUrlByHash() {
        ResponseEntity<String> shortUrl = urlShorterController.createShortUrl(createShortUrlCommand());
        Assertions.assertEquals(shortUrl.getStatusCode(), HttpStatus.OK);
        Assertions.assertNotNull(shortUrl.getBody());

        String hash = StringUtils.replace(shortUrl.getBody(), ORIGINAL_URL, "");
        Assertions.assertNotNull(urlShorterController.getUrlByHash(hash));

        Assertions.assertEquals(urlShorterController.getUrlByHash(hash + "a").getStatusCode(), HttpStatus.NOT_FOUND);
    }


    private CustomUrlCommand createShortUrlCommand(){
        CustomUrlCommand shortUrlCommand = new CustomUrlCommand();
        shortUrlCommand.setHash("123abc@");
        shortUrlCommand.setLoginName("gustavon");
        shortUrlCommand.setExpirationDays(2);
        shortUrlCommand.setOriginalUrl(ORIGINAL_URL);
        return shortUrlCommand;
    }
}