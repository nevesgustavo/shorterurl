package br.com.neves.shorturl.exception;

public class UrlExpiredException extends RuntimeException {
// --------------------------- CONSTRUCTORS ---------------------------

    public UrlExpiredException(String message) {
        super(message);
    }
}
