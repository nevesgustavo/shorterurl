package br.com.neves.shorturl.service;

import br.com.neves.shorturl.exception.UrlNotFoundException;
import br.com.neves.shorturl.jpa.model.relational.ShortUrl;
import br.com.neves.shorturl.jpa.repository.relational.RelationalShortUrlRepository;
import br.com.neves.shorturl.jpa.repository.relational.ShortUrlLogRepository;
import br.com.neves.shorturl.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@SpringBootTest
@TestPropertySource(properties = {"database = postgres"})
class RelationalShorterUrlServiceImplTest {
// ------------------------------ FIELDS ------------------------------

    private static final String ORIGINAL_URL = "http://localhost:8080/home";

    @Autowired
    private ShorterUrlService relationalShorterUrlServiceImpl;

    @Autowired
    private RelationalShortUrlRepository relationalShortUrlRepository;

    @Autowired
    private ShortUrlLogRepository shortUrlLogRepository;

// -------------------------- OTHER METHODS --------------------------

    @Test
    void createCustomShortUrlRelational() {
        String shortUrl = relationalShorterUrlServiceImpl.createCustomShortUrl("abc", ORIGINAL_URL, 1);
        Assertions.assertNotNull(shortUrl, "The url must exists");
        Assertions.assertTrue(StringUtils.contains(shortUrl, "abc"), "the custom url must contain the custom hash");
    }

    @Test
    void createShortUrlRelational() {
        String shortUrl = relationalShorterUrlServiceImpl.createShortUrl("gustavon", ORIGINAL_URL, 1);
        Assertions.assertNotNull(shortUrl);
    }

    @Test
    void generateHashRelational() {
        Assertions.assertNotNull(relationalShorterUrlServiceImpl.generateHash("gustavon"));
    }

    @Test
    void getOriginalUrlByHashAndSaveLogRelational() {
        ShortUrl shortUrl = createNewShortUrlExampleRelational(1);
        Assertions.assertNotNull(relationalShorterUrlServiceImpl.getOriginalUrlByHashAndSaveLog(shortUrl.getHash()));
        Assertions.assertTrue(shortUrlLogRepository.existsByHash(shortUrl.getHash()));
    }

    private ShortUrl createNewShortUrlExampleRelational(Integer expirationDays) {
        ShortUrl shortUrl = new ShortUrl(relationalShorterUrlServiceImpl.generateHash("gustavon"), ORIGINAL_URL);
        shortUrl.setExpirationDate(DateUtil.sumDaysInCurrentDate(expirationDays));
        return relationalShortUrlRepository.save(shortUrl);
    }

    @BeforeEach
    public void init() {
        relationalShortUrlRepository.deleteAll();
    }

    @Test
    void removeExpiredHashIfExistsRelational() {
        ShortUrl newShortUrlExample = createNewShortUrlExampleRelational(-1);
        Assertions.assertNotNull(newShortUrlExample);

        relationalShorterUrlServiceImpl.removeExpiredHashIfExists(newShortUrlExample.getHash());
        Assertions.assertThrows(UrlNotFoundException.class, () -> relationalShorterUrlServiceImpl.getOriginalUrlByHashAndSaveLog(newShortUrlExample.getHash()));
    }
}