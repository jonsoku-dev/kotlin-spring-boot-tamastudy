package com.tamastudy.tama.config.security

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import java.time.Duration
import java.util.*
import javax.annotation.PostConstruct
import javax.servlet.http.HttpServletRequest


@Component
class JwtTokenProvider(
        private val redisTemplate: RedisTemplate<*, *>,
        @Qualifier("customUserDetailsService") private val userDetailsService: UserDetailsService,
) {
    //    @Value("spring.jwt.secret")
    var secretKey: String = "c2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQtc2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQK"

    //        var tokenValidMillisecond = 1000L * 60 * 30 // 30분
    var tokenValidMillisecond = 2000

//    var refreshTokenValidMillisecond = 1000L * 60 * 60 * 24 * 7 // 7일
    var refreshTokenValidMillisecond = 20000

//    @PostConstruct
//    protected fun init() {
//        secretKey = Base64.getEncoder().encodeToString(secretKey!!.toByteArray())
//    }

    // generate jwt token
    fun createToken(email: String): String? {
        val claims = Jwts.claims().setSubject(email)
        val now = Date()

        val keyBytes = Decoders.BASE64.decode(secretKey)
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
        val keyBytes = Decoders.BASE64.decode(secretKey)
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
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).body.subject
        } catch (e: ExpiredJwtException) {
            e.claims.subject
        }
    }

    // Header 에서 토큰을 얻음
    fun resolveToken(req: HttpServletRequest): String? {
        return req.getHeader("Authorization")
    }

    fun getRemainingSeconds(jwtToken: String): Duration {
        val claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwtToken)
        val seconds = (claims.body.expiration.time - claims.body.issuedAt.time) / 1000
        return Duration.ofSeconds(if (seconds < 0) 0 else seconds)
    }

    // jwt 토큰의 유효성 + 로그아웃 확인 + 만료일자 확인
    fun validateToken(jwtToken: String?): Boolean {
        return try {
            if(jwtToken == null) return false
            if (isLoggedOut(jwtToken)) return false
            val claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwtToken)
            println(claims)
            !claims.body.expiration.before(Date())
        } catch (e: Exception) {
            false
        }
    }

    // jwt 토큰의 유효성 + 로그아웃 확인 + 만료일자만 초과한 토큰이면 return true;
    fun validateTokenExceptExpiration(jwtToken: String?): Boolean {
        return try {
            if (isLoggedOut(jwtToken!!)) return false
            val claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwtToken)
            claims.body.expiration.before(Date())
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