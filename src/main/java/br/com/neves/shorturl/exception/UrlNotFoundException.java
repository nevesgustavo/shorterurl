package br.com.neves.shorturl.exception;

public class UrlNotFoundException extends RuntimeException {
// --------------------------- CONSTRUCTORS ---------------------------

    public UrlNotFoundException(String message) {
        super(message);
    }
}
