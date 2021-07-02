package br.com.neves.shorturl.jpa.model.nosql;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.LocalDateTime;

@Document("shortUrl")
public class ShortUrl implements Serializable {
// ------------------------------ FIELDS ------------------------------

    @Id
    private String id;

    @Field("hash")
    @TextIndexed
    private String hash;

    @Field("originalUrl")
    private String originalUrl;

    @Field("expirationDate")
    private java.time.LocalDateTime expirationDate;

    @Field("createdAt")
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }
}
