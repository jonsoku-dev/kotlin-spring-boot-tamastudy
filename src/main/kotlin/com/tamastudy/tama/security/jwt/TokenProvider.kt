package com.tamastudy.tama.security.jwt

import com.tamastudy.tama.security.auth.PrincipalDetails
import com.tamastudy.tama.repository.UserRepository
import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*

@Component
class TokenProvider(
        @Value("\${jwt.secret}") configSecret: String,
        @Value("\${jwt.token-validity-in-seconds}") tokenValidityInSeconds: Long,
        private val userRepository: UserRepository
) : InitializingBean {

    private val logger: Logger = LoggerFactory.getLogger(TokenProvider::class.java)

    private val CLAIM_ID = "id"

    private var secret: String? = null
    private var tokenValidityInMilliseconds: Long = 0

    private var key: Key? = null


    init {
        secret = configSecret
        tokenValidityInMilliseconds = tokenValidityInSeconds * 1000
    }

    override fun afterPropertiesSet() {
        val keyBytes = Decoders.BASE64.decode(secret)
        key = Keys.hmacShaKeyFor(keyBytes)
    }

    fun createToken(authentication: Authentication): String? {
        val userDetails = authentication.principal as PrincipalDetails
        println("userDto: ${userDetails.getUserDto()}")
        val now: Long = Date().time
        val validity = Date(now + tokenValidityInMilliseconds)
        return Jwts.builder()
                .setSubject(authentication.name)
                .claim(CLAIM_ID, userDetails.getId())
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact()
    }

    fun getAuthentication(token: String?): Authentication? {
        val claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .body

        println("=====")
        println(claims)
        println("=====")

        val userId = claims[CLAIM_ID].toString().toLong()

        val principalDetails = userRepository.findByIdOrNull(userId)?.let { userEntity ->
            PrincipalDetails(userEntity)
        } ?: throw IllegalArgumentException("유저가 존재하지 않습니다. 토큰을 확인해주세요. ")

        return UsernamePasswordAuthenticationToken(principalDetails, token, principalDetails.authorities)
    }

    fun validateToken(token: String?): Boolean {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
            return true
        } catch (e: SecurityException) {
            logger.info("잘못된 JWT 서명입니다.")
        } catch (e: MalformedJwtException) {
            logger.info("잘못된 JWT 서명입니다.")
        } catch (e: ExpiredJwtException) {
            logger.info("만료된 JWT 토큰입니다.")
        } catch (e: UnsupportedJwtException) {
            logger.info("지원되지 않는 JWT 토큰입니다.")
        } catch (e: IllegalArgumentException) {
            logger.info("JWT 토큰이 잘못되었습니다.")
        }
        return false
    }
}