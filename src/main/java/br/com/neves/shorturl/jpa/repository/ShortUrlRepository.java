package br.com.neves.shorturl.jpa.repository;

import br.com.neves.shorturl.jpa.model.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {
// -------------------------- OTHER METHODS --------------------------

    boolean existsByHash(String hash);

    void deleteByHashAndExpirationDateBefore(String hash, LocalDateTime currentDateTime);

    ShortUrl findByHashAndExpirationDateAfter(String hash, LocalDateTime currentDateTime);
}
