package br.com.neves.shorturl.configuration;

import br.com.neves.shorturl.service.ShorterUrlService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DbConfiguration {
// -------------------------- OTHER METHODS --------------------------

    @Bean
    @ConditionalOnProperty(value = "database", havingValue = "mongodb")
    public ShorterUrlService noSqlProvider() {
        return new br.com.neves.shorturl.service.impl.nosql.NoSqlShorterUrlServiceImpl();
    }

    @Bean
    @ConditionalOnProperty(value = "database", havingValue = "postgres")
    public ShorterUrlService relationalProvider() {
        return new br.com.neves.shorturl.service.impl.relational.ShorterUrlServiceImpl();
    }
}
