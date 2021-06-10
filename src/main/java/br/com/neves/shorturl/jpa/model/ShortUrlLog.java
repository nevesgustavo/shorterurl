package br.com.neves.shorturl.jpa.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "shortUrlLog")
public class ShortUrlLog {
// ------------------------------ FIELDS ------------------------------

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "hash", nullable = false)
    private String hash;

    @Column(name = "originalUrl", nullable = false, length = 8000)
    private String originalUrl;

    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;

// --------------------------- CONSTRUCTORS ---------------------------

    public ShortUrlLog() {
        this.createdAt = LocalDateTime.now();
    }

    public ShortUrlLog(String hash, String originalUrl) {
        this();
        this.hash = hash;
        this.originalUrl = originalUrl;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }
}
