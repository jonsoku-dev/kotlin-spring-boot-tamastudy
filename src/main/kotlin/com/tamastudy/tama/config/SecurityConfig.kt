package com.tamastudy.tama.config

import com.tamastudy.tama.config.security.CustomAccessDeniedHandler
import com.tamastudy.tama.config.security.CustomAuthenticationEntryPoint
import com.tamastudy.tama.config.security.JwtAuthenticationFilter
import com.tamastudy.tama.config.security.JwtTokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.BeanIds
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.csrf.CsrfFilter
import org.springframework.web.filter.CharacterEncodingFilter


@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtTokenProvider: JwtTokenProvider,
) : WebSecurityConfigurerAdapter() {

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean(name = [BeanIds.AUTHENTICATION_MANAGER])
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
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
            .httpBasic().disable() // rest api 방식으로 disable
            .csrf().disable() // token 방식으로 disable
            .formLogin().disable() // token 방식으로 disable
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // session 이용하지 않음
            .and()
            .headers().frameOptions().sameOrigin()
            .and()
            .authorizeRequests()
            .antMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
            .antMatchers("/api/v1/user/login").permitAll()
            .antMatchers("/api/v1/user/join").permitAll()
            .antMatchers("/api/v1/user/refresh").permitAll()
            .antMatchers(HttpMethod.GET, "/api/v1/board").permitAll()
            .antMatchers(HttpMethod.GET, "/api/v1/bo").permitAll()
            .antMatchers(HttpMethod.GET, "/api/v1/board/ids").permitAll()
            .antMatchers(HttpMethod.GET, "/api/v2/board").permitAll()
            .antMatchers(HttpMethod.GET, "/api/v1/board/{^[\\d]$}").permitAll()
            .antMatchers(HttpMethod.GET, "/api/v1/board/{^[\\d]$}/comment").permitAll()
            .antMatchers(HttpMethod.GET, "/api/v1/category").permitAll()
            .anyRequest().authenticated()
            .and().exceptionHandling().accessDeniedHandler(CustomAccessDeniedHandler())
            .and().exceptionHandling().authenticationEntryPoint(CustomAuthenticationEntryPoint())

        http.addFilterBefore(
            JwtAuthenticationFilter(jwtTokenProvider),
            UsernamePasswordAuthenticationFilter::class.java
        )
    }
}

/*
 * 권한주는법
 * .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
 */