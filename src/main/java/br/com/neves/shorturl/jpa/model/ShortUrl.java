package br.com.neves.shorturl.jpa.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "shortUrl", indexes = {@Index(name = "uniqueIndex", columnList = "hash", unique = true)})
public class ShortUrl {
// ------------------------------ FIELDS ------------------------------

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "hash", nullable = false)
    private String hash;

    @Column(name = "original_url", nullable = false, length = 8000)
    private String originalUrl;

    @Column(name = "expiration_date", nullable = false)
    private java.time.LocalDateTime expirationDate;

    @Column(name = "createdAt", nullable = false)
    private java.time.LocalDateTime createdAt;

// --------------------------- CONSTRUCTORS ---------------------------

    public ShortUrl() {
        this.createdAt = java.time.LocalDateTime.now();
    }

    public ShortUrl(String hash, String originalUrl) {
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

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
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
