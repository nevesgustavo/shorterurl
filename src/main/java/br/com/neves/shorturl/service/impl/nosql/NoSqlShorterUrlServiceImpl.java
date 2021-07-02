package br.com.neves.shorturl.service.impl.nosql;

import br.com.neves.shorturl.exception.HashAlreadyExistsException;
import br.com.neves.shorturl.exception.UrlExpiredException;
import br.com.neves.shorturl.exception.UrlNotFoundException;
import br.com.neves.shorturl.jpa.model.nosql.ShortUrl;
import br.com.neves.shorturl.jpa.repository.nosql.NoSqlShortUrlRepository;
import br.com.neves.shorturl.service.ShorterUrlService;
import br.com.neves.shorturl.service.hashGenerator.HashGenerator;
import br.com.neves.shorturl.utils.DateUtil;
import br.com.neves.shorturl.utils.HashGeneratorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class NoSqlShorterUrlServiceImpl implements ShorterUrlService {
// ------------------------------ FIELDS ------------------------------

    @Value("${domain}")
    private String domain;

    @Value("${hash.expiration.days}")
    private Integer expirationDays;

    @Autowired
    private HashGenerator hashGenerator;

    @Autowired
    private NoSqlShortUrlRepository noSqlShortUrlRepository;

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface ShorterUrlService ---------------------

    @Override
    public String createCustomShortUrl(String customHash, String originalUrl, Integer expirationDays) {
        prepareExpirationDateRemoveExpiredShortUrlAndSaveShortUrl(new ShortUrl(customHash, originalUrl), expirationDays);
        return String.format("%s%s", domain, customHash);
    }

    @Override
    public String createShortUrl(String loginName, String originalUrl, Integer expirationDays) {
        String hash = generateHash(loginName);
        prepareExpirationDateRemoveExpiredShortUrlAndSaveShortUrl(new ShortUrl(hash, originalUrl), expirationDays);
        return String.format("%s%s", domain, hash);
    }

    @Override
    public String generateHash(String loginName) {
        return HashGeneratorUtil.generateHash(hashGenerator, loginName);
    }

    @Override
    public String getOriginalUrlByHashAndSaveLog(String hash) {
        ShortUrl shortUrl = noSqlShortUrlRepository.findByHashAndExpirationDateAfter(hash, LocalDateTime.now());

        if (shortUrl == null && noSqlShortUrlRepository.existsByHash(hash))
            throw new UrlExpiredException("The url is expired");

        if (shortUrl == null)
            throw new UrlNotFoundException("The url was not found");

        return shortUrl.getOriginalUrl();
    }

// -------------------------- OTHER METHODS --------------------------

    private void prepareExpirationDateRemoveExpiredShortUrlAndSaveShortUrl(ShortUrl shortUrl, Integer expirationDays) {
        if (expirationDays == null)
            expirationDays = this.expirationDays;

        removeExpiredHashIfExists(shortUrl.getHash());
        shortUrl.setExpirationDate(DateUtil.sumDaysInCurrentDate(expirationDays));
        saveShortUrlOnRepository(shortUrl);
    }

    @Override
    public void removeExpiredHashIfExists(String hash) {
        noSqlShortUrlRepository.deleteByHashAndExpirationDateBefore(hash, LocalDateTime.now());
    }

    private void saveShortUrlOnRepository(ShortUrl shortUrl) throws HashAlreadyExistsException {
        if (noSqlShortUrlRepository.existsByHash(shortUrl.getHash()))
            throw new HashAlreadyExistsException("The hash already exists");

        noSqlShortUrlRepository.save(shortUrl);
    }
}
