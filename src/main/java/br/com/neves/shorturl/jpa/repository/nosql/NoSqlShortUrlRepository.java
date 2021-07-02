package br.com.neves.shorturl.jpa.repository.nosql;

import br.com.neves.shorturl.jpa.model.nosql.ShortUrl;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface NoSqlShortUrlRepository extends MongoRepository<ShortUrl, Long> {
// -------------------------- OTHER METHODS --------------------------

    void deleteByHashAndExpirationDateBefore(String hash, LocalDateTime currentDateTime);

    boolean existsByHash(String hash);

    ShortUrl findByHashAndExpirationDateAfter(String hash, LocalDateTime currentDateTime);
}
