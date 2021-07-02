package br.com.neves.shorturl.service;

import br.com.neves.shorturl.exception.UrlNotFoundException;
import br.com.neves.shorturl.jpa.repository.nosql.NoSqlShortUrlRepository;
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
@TestPropertySource(properties = {"database = mongodb"})
public class NoSqlShortUrlServiceImplTest {
// ------------------------------ FIELDS ------------------------------

    private static final String ORIGINAL_URL = "http://localhost:8085/home";

    @Autowired
    private ShorterUrlService noSqlShorterUrlServiceImpl;

    @Autowired
    private NoSqlShortUrlRepository noSqlShortUrlRepository;

// -------------------------- OTHER METHODS --------------------------

    @Test
    void createCustomShortUrlNoSql() {
        String shortUrl = noSqlShorterUrlServiceImpl.createCustomShortUrl("abc", ORIGINAL_URL, 1);
        Assertions.assertNotNull(shortUrl, "The url must exists");
        Assertions.assertTrue(StringUtils.contains(shortUrl, "abc"), "the custom url must contain the custom hash");
    }

    @Test
    void createShortUrlNoSql() {
        String shortUrl = noSqlShorterUrlServiceImpl.createShortUrl("gustavon", ORIGINAL_URL, 1);
        Assertions.assertNotNull(shortUrl);
    }

    @Test
    void generateHashNoSql() {
        Assertions.assertNotNull(noSqlShorterUrlServiceImpl.generateHash("gustavon"));
    }

    @BeforeEach
    public void init() {
        noSqlShortUrlRepository.deleteAll();
    }

    @Test
    void removeExpiredHashIfExistsNoSql() {
        br.com.neves.shorturl.jpa.model.nosql.ShortUrl newShortUrlExample = createNewShortUrlExampleNoSql(-1);
        Assertions.assertNotNull(newShortUrlExample);

        noSqlShorterUrlServiceImpl.removeExpiredHashIfExists(newShortUrlExample.getHash());
        Assertions.assertThrows(UrlNotFoundException.class, () -> noSqlShorterUrlServiceImpl.getOriginalUrlByHashAndSaveLog(newShortUrlExample.getHash()));
    }

    private br.com.neves.shorturl.jpa.model.nosql.ShortUrl createNewShortUrlExampleNoSql(Integer expirationDays) {
        br.com.neves.shorturl.jpa.model.nosql.ShortUrl shortUrl = new br.com.neves.shorturl.jpa.model.nosql.ShortUrl(noSqlShorterUrlServiceImpl.generateHash("gustavon"), ORIGINAL_URL);
        shortUrl.setExpirationDate(DateUtil.sumDaysInCurrentDate(expirationDays));
        return noSqlShortUrlRepository.save(shortUrl);
    }
}
