package br.com.neves.shorturl.configuration;

import br.com.neves.shorturl.service.hashGenerator.HashGenerator;
import br.com.neves.shorturl.service.hashGenerator.MD5HashGenerator;
import br.com.neves.shorturl.service.hashGenerator.SHA2HashGenerator;
import br.com.neves.shorturl.service.hashGenerator.XXH32HashGenerator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HashGeneratorConfiguration {
// -------------------------- OTHER METHODS --------------------------

    @Bean
    @ConditionalOnProperty(value = "hash.type", havingValue = "XXH32")
    public HashGenerator XXH32HashGenerator() {
        return new XXH32HashGenerator();
    }

    @Bean
    @ConditionalOnProperty(value = "hash.type", havingValue = "MD5")
    public HashGenerator md5HashGenerator() {
        return new MD5HashGenerator();
    }

    @Bean
    @ConditionalOnProperty(value = "hash.type", havingValue = "SHA256")
    public HashGenerator sha256HashGenerator() {
        return new SHA2HashGenerator();
    }
}
