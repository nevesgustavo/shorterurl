package br.com.neves.shorturl.service.impl.relational;

import br.com.neves.shorturl.exception.*;
import br.com.neves.shorturl.jpa.model.relational.ShortUrl;
import br.com.neves.shorturl.jpa.repository.relational.RelationalShortUrlRepository;
import br.com.neves.shorturl.service.ShorterUrlService;
import br.com.neves.shorturl.service.hashGenerator.HashGenerator;
import br.com.neves.shorturl.utils.DateUtil;
import br.com.neves.shorturl.utils.HashGeneratorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;


@Transactional
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class ShorterUrlServiceImpl implements ShorterUrlService {
// ------------------------------ FIELDS ------------------------------

    @Value("${domain}")
    private String domain;

    @Value("${hash.expiration.days}")
    private Integer expirationDays;

    @Autowired
    private HashGenerator hashGenerator;

    @Autowired
    private RelationalShortUrlRepository relationalShortUrlRepository;

    @Autowired
    private ShortUrlLogService shortUrlLogService;

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface ShorterUrlService ---------------------

    public String createCustomShortUrl(String customHash, String originalUrl, Integer expirationDays) {
        prepareExpirationDateRemoveExpiredShortUrlAndSaveShortUrl(new ShortUrl(customHash, originalUrl), expirationDays);
        return String.format("%s%s", domain, customHash);
    }

    public String createShortUrl(String loginName, String originalUrl, Integer expirationDays) {
        String hash = generateHash(loginName);
        prepareExpirationDateRemoveExpiredShortUrlAndSaveShortUrl(new ShortUrl(hash, originalUrl), expirationDays);
        return String.format("%s%s", domain, hash);
    }

    public String generateHash(String loginName) {
        return HashGeneratorUtil.generateHash(hashGenerator, loginName);
    }

    public String getOriginalUrlByHashAndSaveLog(String hash) {
        ShortUrl shortUrl = relationalShortUrlRepository.findByHashAndExpirationDateAfter(hash, LocalDateTime.now());

        if (shortUrl == null && relationalShortUrlRepository.existsByHash(hash))
            throw new UrlExpiredException("The url is expired");

        if (shortUrl == null)
            throw new UrlNotFoundException("The url was not found");

        shortUrlLogService.save(shortUrl);

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

    public void removeExpiredHashIfExists(String hash) {
        relationalShortUrlRepository.deleteByHashAndExpirationDateBefore(hash, LocalDateTime.now());
    }

    private void saveShortUrlOnRepository(ShortUrl shortUrl) throws HashAlreadyExistsException {
        if (relationalShortUrlRepository.existsByHash(shortUrl.getHash()))
            throw new HashAlreadyExistsException("The hash already exists");

        relationalShortUrlRepository.save(shortUrl);
    }
}
