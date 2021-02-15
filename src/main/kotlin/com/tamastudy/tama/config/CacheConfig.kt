package com.tamastudy.tama.config

import org.springframework.cache.annotation.CachingConfigurerSupport
import org.springframework.cache.interceptor.KeyGenerator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CacheConfig : CachingConfigurerSupport() {
    @Bean("boardCacheGenerator")
    override fun keyGenerator(): KeyGenerator {
        return BoardCacheGenerator()
    }
}