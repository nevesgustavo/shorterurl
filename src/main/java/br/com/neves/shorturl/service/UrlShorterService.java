package br.com.neves.shorturl.service;

import br.com.neves.shorturl.exception.HashAlreadyExistsException;
import br.com.neves.shorturl.exception.HashGeneratorNotFoundException;
import br.com.neves.shorturl.exception.UrlExpiredException;
import br.com.neves.shorturl.exception.UrlNotFoundException;
import br.com.neves.shorturl.jpa.model.ShortUrl;
import br.com.neves.shorturl.jpa.repository.ShortUrlRepository;
import br.com.neves.shorturl.service.hashGenerator.HashGenerator;
import br.com.neves.shorturl.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class UrlShorterService {
// ------------------------------ FIELDS ------------------------------

    @Value("${domain}")
    private String domain;

    @Value("${hash.expiration.days}")
    private Integer expirationDays;

    @Autowired
    private List<HashGenerator> hashGenerators;

    @Autowired
    private ShortUrlRepository shortUrlRepository;

    @Autowired
    private ShortUrlLogService shortUrlLogService;

// -------------------------- OTHER METHODS --------------------------

    public String createCustomShortUrl(String customHash, String originalUrl, Integer expirationDays) {
        prepareExpirationDateRemoveExpiredShorUrlAndSaveShortUrl(new ShortUrl(customHash, originalUrl), expirationDays);
        return String.format("%s%s", domain, customHash);
    }

    private void prepareExpirationDateRemoveExpiredShorUrlAndSaveShortUrl(ShortUrl shortUrl, Integer expirationDays) {
        if (expirationDays == null)
            expirationDays = this.expirationDays;

        removeExpiredHashIfExists(shortUrl);
        shortUrl.setExpirationDate(DateUtil.sumDaysInCurrentDate(expirationDays));
        saveShortUrlOnRepository(shortUrl);
    }

    public void removeExpiredHashIfExists(ShortUrl shortUrl) {
        shortUrlRepository.deleteByHashAndExpirationDateBefore(shortUrl.getHash(), LocalDateTime.now());
    }

    private void saveShortUrlOnRepository(ShortUrl shortUrl) throws HashAlreadyExistsException {
        if (shortUrlRepository.existsByHash(shortUrl.getHash()))
            throw new HashAlreadyExistsException("The hash already exists");

        shortUrlRepository.save(shortUrl);
    }

    public String createShortUrl(String loginName, String originalUrl, Integer expirationDays) {
        String hash = generateHash(loginName);
        prepareExpirationDateRemoveExpiredShorUrlAndSaveShortUrl(new ShortUrl(hash, originalUrl), expirationDays);
        return String.format("%s%s", domain, hash);
    }

    public String generateHash(String loginName) {
        HashGenerator hashGenerator = getActiveHashGenerator();
        if (hashGenerator == null)
            throw new HashGeneratorNotFoundException("The hash generator is not found");

        return hashGenerator.generateHash(loginName);
    }

    private HashGenerator getActiveHashGenerator() {
        return hashGenerators.stream()
                .filter(d -> d.isActive())
                .findFirst()
                .orElse(null);
    }

    public String getOriginalUrlByHashAndSaveLog(String hash) {
        ShortUrl shortUrl = shortUrlRepository.findByHashAndExpirationDateAfter(hash, LocalDateTime.now());

        if (shortUrl == null && shortUrlRepository.existsByHash(hash))
            throw new UrlExpiredException("The url is expired");

        if (shortUrl == null)
            throw new UrlNotFoundException("The url was not found");

        shortUrlLogService.save(shortUrl);

        return shortUrl.getOriginalUrl();
    }
}
