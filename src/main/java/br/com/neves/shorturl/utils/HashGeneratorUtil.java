package br.com.neves.shorturl.utils;

import br.com.neves.shorturl.exception.HashGeneratorIOException;
import br.com.neves.shorturl.exception.HashGeneratorNotFoundException;
import br.com.neves.shorturl.service.hashGenerator.HashGenerator;
import org.joda.time.DateTime;

import java.io.IOException;

public class HashGeneratorUtil {
// -------------------------- STATIC METHODS --------------------------

    public static String prepareTextToGenerateHash(String loginName) {
        return String.format("%s_%s", loginName, DateTime.now());
    }

    public static String generateHash(HashGenerator hashGenerator, String loginName) {
        if (hashGenerator == null)
            throw new HashGeneratorNotFoundException("The hash generator is not found");

        try {
            return hashGenerator.generateHash(loginName);
        } catch (IOException e) {
            throw new HashGeneratorIOException(String.format("The hash can't be generated: %s", e.getMessage()));
        }
    }
}
