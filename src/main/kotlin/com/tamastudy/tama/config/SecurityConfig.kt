package com.tamastudy.tama.config

import com.tamastudy.tama.security.jwt.JwtSecurityConfig
import com.tamastudy.tama.security.jwt.TokenProvider
import com.tamastudy.tama.repository.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
@EnableWebSecurity
class SecurityConfig(
        private val tokenProvider: TokenProvider,
//        private val corsFilter: CorsFilter,
        private val userRepository: UserRepository
) : WebSecurityConfigurerAdapter() {

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    override fun configure(http: HttpSecurity) {
        http
                .cors()
                .and()// @CrossOrigin(인증X), 시큐리티 필터에 등록 인증 (O) -> 여기서 한방에
                .csrf().disable() // token 방식으로 사용
                .exceptionHandling()
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // session 이용하지 않음
                .and()
                .authorizeRequests()
                // User
                .antMatchers("/api/v1/user/login", "/api/v1/user/join", "/api/v1/user/authenticated").permitAll()
                .antMatchers("/api/v1/user/**").authenticated()
                // Board Category
                .antMatchers(HttpMethod.GET, "/api/v1/category/**").permitAll()
                .antMatchers("/api/v1/category/**").authenticated()
                // Board
                .antMatchers(HttpMethod.GET, "/api/v1/board/**").permitAll()
                .antMatchers("/api/v1/board/**").authenticated()
                // Etc...
                .anyRequest().permitAll()
                .and()
                .apply(JwtSecurityConfig(tokenProvider))

    }
}

/*
 * 권한주는법
 * .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
 */