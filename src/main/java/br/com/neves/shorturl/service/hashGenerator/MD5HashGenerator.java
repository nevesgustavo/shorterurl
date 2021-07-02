package br.com.neves.shorturl.service.hashGenerator;

import br.com.neves.shorturl.utils.HashGeneratorUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;


public class MD5HashGenerator implements HashGenerator {
// ------------------------------ FIELDS ------------------------------

    private static final String MD5 = "MD5";

    @Value("${hash.type}")
    private String hashType;

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface HashGenerator ---------------------

    @Override
    public String generateHash(String loginName) {
        return DigestUtils.md5Hex(HashGeneratorUtil.prepareTextToGenerateHash(loginName));
    }

    @Override
    public boolean isActive() {
        return StringUtils.equalsIgnoreCase(hashType, MD5);
    }
}
