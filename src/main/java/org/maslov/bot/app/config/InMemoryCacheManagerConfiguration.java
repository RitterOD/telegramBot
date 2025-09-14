package org.maslov.bot.app.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InMemoryCacheManagerConfiguration {

    @Bean
    CacheManager inMemoryCacheManager() {
        return new ConcurrentMapCacheManager();
    }
}
