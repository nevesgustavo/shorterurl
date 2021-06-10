package br.com.neves.shorturl.jpa.repository;

import br.com.neves.shorturl.jpa.model.ShortUrlLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShortUrlLogRepository extends JpaRepository<ShortUrlLog, Long> {
// -------------------------- OTHER METHODS --------------------------

    boolean existsByHash(String hash);
}
