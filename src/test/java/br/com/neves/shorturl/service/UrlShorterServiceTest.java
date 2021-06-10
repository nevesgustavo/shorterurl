package br.com.neves.shorturl.service;

import br.com.neves.shorturl.exception.UrlNotFoundException;
import br.com.neves.shorturl.jpa.model.ShortUrl;
import br.com.neves.shorturl.jpa.model.ShortUrlLog;
import br.com.neves.shorturl.jpa.repository.ShortUrlLogRepository;
import br.com.neves.shorturl.jpa.repository.ShortUrlRepository;
import br.com.neves.shorturl.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UrlShorterServiceTest {
    private static final String ORIGINAL_URL = "http://localhost:8080/home";

    @Autowired
    private UrlShorterService urlShorterService;

    @Autowired
    private ShortUrlRepository shortUrlRepository;

    @Autowired
    private ShortUrlLogRepository shortUrlLogRepository;

    @Test
    void createCustomShortUrl() {
        String shortUrl = urlShorterService.createCustomShortUrl("abc", ORIGINAL_URL, 1);
        Assertions.assertNotNull(shortUrl, "The url must exists");
        Assertions.assertTrue(StringUtils.contains(shortUrl, "abc"), "the custom url must contain the custom hash");
    }

    @Test
    void removeExpiredHashIfExists() {
        ShortUrl newShortUrlExample = createNewShortUrlExample(-1);
        Assertions.assertNotNull(newShortUrlExample);

        urlShorterService.removeExpiredHashIfExists(newShortUrlExample);
        Assertions.assertThrows(UrlNotFoundException.class, () -> urlShorterService.getOriginalUrlByHashAndSaveLog(newShortUrlExample.getHash()));
    }

    @Test
    void createShortUrl() {
        String shortUrl = urlShorterService.createShortUrl("gustavon", ORIGINAL_URL, 1);
        Assertions.assertNotNull(shortUrl);
    }

    @Test
    void generateHash() {
        Assertions.assertNotNull(urlShorterService.generateHash("gustavon"));
    }

    @Test
    void getOriginalUrlByHashAndSaveLog() {
        ShortUrl shortUrl = createNewShortUrlExample(1);
        Assertions.assertNotNull(urlShorterService.getOriginalUrlByHashAndSaveLog(shortUrl.getHash()));
        Assertions.assertTrue(shortUrlLogRepository.existsByHash(shortUrl.getHash()));
    }

    private ShortUrl createNewShortUrlExample(Integer expirationDays){
        ShortUrl shortUrl = new ShortUrl(urlShorterService.generateHash("gustavon"), ORIGINAL_URL);
        shortUrl.setExpirationDate(DateUtil.sumDaysInCurrentDate(expirationDays));
        return shortUrlRepository.save(shortUrl);
    }
}