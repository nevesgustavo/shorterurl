package br.com.neves.shorturl.service.hashGenerator;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface HashGenerator {
    String generateHash(String loginName) throws IOException;
    boolean isActive();
}
