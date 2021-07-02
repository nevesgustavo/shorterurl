package br.com.neves.shorturl.jpa.repository.relational;

import br.com.neves.shorturl.jpa.model.relational.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface RelationalShortUrlRepository extends JpaRepository<ShortUrl, Long> {
// -------------------------- OTHER METHODS --------------------------

    boolean existsByHash(String hash);

    void deleteByHashAndExpirationDateBefore(String hash, LocalDateTime currentDateTime);

    ShortUrl findByHashAndExpirationDateAfter(String hash, LocalDateTime currentDateTime);
}
