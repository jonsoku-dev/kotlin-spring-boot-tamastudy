package com.tamastudy.tama.config.security

import com.tamastudy.tama.config.property.JwtProperties
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import java.time.Duration
import java.util.*
import javax.servlet.http.HttpServletRequest


@Component
class JwtTokenProvider(
        private val jwtProperties: JwtProperties,
        private val redisTemplate: RedisTemplate<*, *>,
        @Qualifier("customUserDetailsService") private val userDetailsService: UserDetailsService,
) {
    var tokenValidMillisecond = jwtProperties.accessMaxAge!! // 30분
    var refreshTokenValidMillisecond = jwtProperties.refreshMaxAge!! // 7일

    // generate jwt token
    fun createToken(email: String): String? {
        val claims = Jwts.claims().setSubject(email)
        val now = Date()

        val keyBytes = Decoders.BASE64.decode(jwtProperties.secretKey)
        val key = Keys.hmacShaKeyFor(keyBytes)

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(Date(now.time + tokenValidMillisecond))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact()
    }

    // generate jwt refresh token
    fun createRefreshToken(): String {
        val now = Date()
        val keyBytes = Decoders.BASE64.decode(jwtProperties.secretKey)
        val key = Keys.hmacShaKeyFor(keyBytes)
        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(Date(now.time + refreshTokenValidMillisecond))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact()
    }

    // jwt token 으로 인증정보 조회
    fun getAuthentication(token: String): Authentication {
        println("getAuthentication: $token")
        val userDetails = userDetailsService.loadUserByUsername(this.getUserEmail(token))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    // jwt 토큰에서 회원 구별 정보 추출 (USER PK)
    fun getUserEmail(token: String?): String {
        println("getUserEmail: $token")
        return try {
            Jwts.parserBuilder().setSigningKey(jwtProperties.secretKey).build().parseClaimsJws(token).body.subject
        } catch (e: ExpiredJwtException) {
            e.claims.subject
        }
    }

    // Header 에서 토큰을 얻음
    fun resolveToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        return if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7, bearerToken.length)
        } else null
    }

    fun getRemainingSeconds(jwtToken: String): Duration {
        val claims = Jwts.parserBuilder().setSigningKey(jwtProperties.secretKey).build().parseClaimsJws(jwtToken)
        val seconds = (claims.body.expiration.time - claims.body.issuedAt.time) / 1000
        return Duration.ofSeconds(if (seconds < 0) 0 else seconds)
    }

    // jwt 토큰의 유효성 + 로그아웃 확인 + 만료일자 확인
    fun validateToken(jwtToken: String?): Boolean {
        return try {
            if (jwtToken == null) return false
            if (isLoggedOut(jwtToken)) return false
            val claims = Jwts.parserBuilder().setSigningKey(jwtProperties.secretKey).build().parseClaimsJws(jwtToken)
            println(claims)
            !claims.body.expiration.before(Date())
        } catch (e: Exception) {
            return false
        }
    }

    // jwt 토큰의 유효성 + 로그아웃 확인 + 만료일자만 초과한 토큰이면 return true;
    fun validateTokenExceptExpiration(jwtToken: String?): Boolean {
        return try {
            if (isLoggedOut(jwtToken!!)) return false
            val claims = Jwts.parserBuilder().setSigningKey(jwtProperties.secretKey).build().parseClaimsJws(jwtToken)
            !claims.body.expiration.before(Date())
        } catch (e: ExpiredJwtException) {
            true
        } catch (e: java.lang.Exception) {
            false
        }
    }

    fun isLoggedOut(jwtToken: String): Boolean {
        return redisTemplate.opsForValue()["token:$jwtToken"] != null
    }
}