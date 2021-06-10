package br.com.neves.shorturl.service.hashGenerator;

public interface HashGenerator {
    String generateHash(String loginName);
    boolean isActive();
}
