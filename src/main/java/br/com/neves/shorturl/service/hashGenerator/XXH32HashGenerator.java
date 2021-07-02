package br.com.neves.shorturl.service.hashGenerator;

import br.com.neves.shorturl.utils.HashGeneratorUtil;
import net.jpountz.xxhash.StreamingXXHash32;
import net.jpountz.xxhash.XXHashFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class XXH32HashGenerator implements HashGenerator {
// ------------------------------ FIELDS ------------------------------

    private static final String XXH32 = "XXH32";

    @Value("${hash.type}")
    private String hashType;

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface HashGenerator ---------------------

    @Override
    public String generateHash(String loginName) throws IOException {
        XXHashFactory factory = XXHashFactory.fastestInstance();
        byte[] data = HashGeneratorUtil.prepareTextToGenerateHash(loginName).getBytes("UTF-8");
        ByteArrayInputStream in = new ByteArrayInputStream(data);

        int seed = 0x9747b28c; // used to initialize the hash value, use whatever
        // value you want, but always the same
        StreamingXXHash32 hash32 = factory.newStreamingHash32(seed);
        byte[] buf = new byte[8192]; // for real-world usage, use a larger buffer, like 8192 bytes
        for (; ; ) {
            int read = in.read(buf);
            if (read == -1) {
                break;
            }
            hash32.update(buf, 0, read);
        }

        int hash = hash32.getValue();

        return String.valueOf(hash);
    }

    @Override
    public boolean isActive() {
        return StringUtils.equalsIgnoreCase(XXH32, hashType);
    }
}
