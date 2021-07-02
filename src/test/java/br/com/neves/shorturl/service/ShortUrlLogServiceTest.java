package br.com.neves.shorturl.service;

import br.com.neves.shorturl.jpa.model.relational.ShortUrl;
import br.com.neves.shorturl.jpa.repository.relational.ShortUrlLogRepository;
import br.com.neves.shorturl.service.impl.relational.ShortUrlLogService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@SpringBootTest
class ShortUrlLogServiceTest {
// ------------------------------ FIELDS ------------------------------

    private static final String ORIGINAL_URL_TEST = "http:localhost:8080/home";
    @Autowired
    private ShortUrlLogService shortUrlLogService;

    @Autowired
    private ShortUrlLogRepository shortUrlLogRepository;

    @Autowired
    private ShorterUrlService shorterUrlService;

// -------------------------- OTHER METHODS --------------------------

    @Test
    void save() {
        String hash = shorterUrlService.generateHash("gustavon");
        shortUrlLogService.save(new ShortUrl(hash, ORIGINAL_URL_TEST));
        Assertions.assertTrue(shortUrlLogRepository.existsByHash(hash));
    }
}