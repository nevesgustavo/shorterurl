package br.com.neves.shorturl.jpa.repository.relational;

import br.com.neves.shorturl.jpa.model.relational.ShortUrlLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShortUrlLogRepository extends JpaRepository<ShortUrlLog, Long> {
// -------------------------- OTHER METHODS --------------------------

    boolean existsByHash(String hash);
}
