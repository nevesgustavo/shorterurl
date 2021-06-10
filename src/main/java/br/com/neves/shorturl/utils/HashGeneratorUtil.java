package br.com.neves.shorturl.utils;

import org.joda.time.DateTime;

public class HashGeneratorUtil {
// -------------------------- STATIC METHODS --------------------------

    public static String prepareTextToGenerateHash(String loginName) {
        return String.format("%s_%s", loginName, DateTime.now());
    }
}
