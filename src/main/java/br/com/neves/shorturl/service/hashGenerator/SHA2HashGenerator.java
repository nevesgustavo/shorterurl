package br.com.neves.shorturl.service.hashGenerator;

import br.com.neves.shorturl.utils.HashGeneratorUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;


public class SHA2HashGenerator implements HashGenerator {
// ------------------------------ FIELDS ------------------------------

    private static final String SHA2 = "SHA256";

    @Value("${hash.type}")
    private String hashType;

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface HashGenerator ---------------------

    @Override
    public String generateHash(String loginName) {
        return DigestUtils.sha256Hex(HashGeneratorUtil.prepareTextToGenerateHash(loginName));
    }

    @Override
    public boolean isActive() {
        return StringUtils.equalsIgnoreCase(hashType, SHA2);
    }
}
