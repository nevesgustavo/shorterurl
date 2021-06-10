package br.com.neves.shorturl.service;

import br.com.neves.shorturl.jpa.model.ShortUrl;
import br.com.neves.shorturl.jpa.repository.ShortUrlLogRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShortUrlLogServiceTest {
// ------------------------------ FIELDS ------------------------------

    private static final String ORIGINAL_URL_TEST = "http:localhost:8080/home";
    @Autowired
    private ShortUrlLogService shortUrlLogService;

    @Autowired
    private ShortUrlLogRepository shortUrlLogRepository;

    @Autowired
    private UrlShorterService urlShorterService;

// -------------------------- OTHER METHODS --------------------------

    @Test
    void save() {
        String hash = urlShorterService.generateHash("gustavon");
        shortUrlLogService.save(new ShortUrl(hash, ORIGINAL_URL_TEST));
        Assertions.assertTrue(shortUrlLogRepository.existsByHash(hash));
    }
}