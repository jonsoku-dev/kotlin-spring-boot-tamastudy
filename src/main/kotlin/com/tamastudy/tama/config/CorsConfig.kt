package com.tamastudy.tama.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
class CorsConfig {
    @Bean
    fun corsFilter(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource();
        val config = CorsConfiguration();
        config.allowCredentials = true // 내 서버가 응답할때 json 을 자바스크립트에서 처리할 수 있게 설정 (자바스크립트로 json 을 사용할 때)
        config.addAllowedOrigin("*") // 모든 ip 에 응답을 허용하겠다.
        config.addAllowedHeader("*") // 모든 header 에 응답을 허용하겠다.
        config.addAllowedMethod("*") // 모든 post, get, put, delete, patch 요청을 허용하겠다.
        source.registerCorsConfiguration("/api/**", config )
        return CorsFilter(source)
    }
}