package com.tamastudy.tama.config

import com.tamastudy.tama.security.jwt.JwtAccessDeniedHandler
import com.tamastudy.tama.security.jwt.JwtAuthenticationEntryPoint
import com.tamastudy.tama.security.jwt.JwtSecurityConfig
import com.tamastudy.tama.security.jwt.TokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.csrf.CsrfFilter
import org.springframework.web.filter.CharacterEncodingFilter


@Configuration
@EnableWebSecurity
class SecurityConfig(
        private val tokenProvider: TokenProvider,
        private val jwtAccessDeniedHandler: JwtAccessDeniedHandler,
        private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint
) : WebSecurityConfigurerAdapter() {

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    override fun configure(web: WebSecurity) {
        web.ignoring()
                .antMatchers("/h2-console/**", "/favicon.ico", "/error")
    }

    override fun configure(http: HttpSecurity) {
        val filter = CharacterEncodingFilter()
        filter.encoding = "UTF-8"
        filter.setForceEncoding(true)

        http.addFilterBefore(filter, CsrfFilter::class.java)
        http
                .cors()
                .and()
                .csrf().disable() // token 방식으로 사용
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler) // enable h2-console
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // session 이용하지 않음
                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/user/login").permitAll()
                .antMatchers("/api/v1/user/join").permitAll()
                .antMatchers("/api/v1/board").permitAll()
                .antMatchers("/api/v1/board/{\\d+}").permitAll()
                .antMatchers("/api/v1/board/{\\d+}/comment").permitAll()
                .antMatchers("/api/v1/category").permitAll()
                .anyRequest().authenticated()
                .and()
                .apply(JwtSecurityConfig(tokenProvider))
    }
}

/*
 * 권한주는법
 * .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
 */