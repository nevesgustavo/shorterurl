package br.com.neves.shorturl.service;

public interface ShorterUrlService {
// -------------------------- OTHER METHODS --------------------------

    String createCustomShortUrl(String customHash, String originalUrl, Integer expirationDays);

    String createShortUrl(String loginName, String originalUrl, Integer expirationDays);

    String generateHash(String loginName);

    String getOriginalUrlByHashAndSaveLog(String hash);

    void removeExpiredHashIfExists(String hash);
}
