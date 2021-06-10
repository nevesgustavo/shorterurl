package br.com.neves.shorturl.service;

import br.com.neves.shorturl.jpa.model.ShortUrl;
import br.com.neves.shorturl.jpa.model.ShortUrlLog;
import br.com.neves.shorturl.jpa.repository.ShortUrlLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShortUrlLogService {
// ------------------------------ FIELDS ------------------------------

    @Autowired
    private ShortUrlLogRepository shortUrlLogRepository;

// -------------------------- OTHER METHODS --------------------------

    public boolean exists(String hash) {
        return shortUrlLogRepository.existsByHash(hash);
    }

    public void save(ShortUrl shortUrl) {
        shortUrlLogRepository.save(new ShortUrlLog(shortUrl.getHash(), shortUrl.getOriginalUrl()));
    }
}
