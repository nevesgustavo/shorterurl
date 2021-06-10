package br.com.neves.shorturl.exception;

public class HashAlreadyExistsException extends RuntimeException {
// --------------------------- CONSTRUCTORS ---------------------------

    public HashAlreadyExistsException(String message) {
        super(message);
    }
}
