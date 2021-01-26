package com.tamastudy.tama.config.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.databind.ObjectMapper
import com.tamastudy.tama.config.auth.PrincipalDetails
import com.tamastudy.tama.entity.User
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


// 스프링 시큐리티에서 UsernamePasswordAuthenticationFilter 가 있음.
// 이 필터는 /login 으로 요청하여 username, password 를 form POST 로 보내면 동작한다.
// 하지만 현재 formLogin 을 disabled 해놓은 상태이기 때문에 자동으로 동작하지 않는다.
// 그래서 이것을 수동으로 등록해보자
class JwtAuthenticationFilter(authenticationManager: AuthenticationManager) : UsernamePasswordAuthenticationFilter() {

    init {
        setAuthenticationManager(authenticationManager)
    }

    // 1. /login 하면 이게 실행되고, 여기서 username 으로 유저를 찾아 있으면 로그인 성공 / 없으면 401 에러를 보낸다.
    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication? {
        try {
            return ObjectMapper().readValue(request.inputStream, User::class.java).let { user ->
                UsernamePasswordAuthenticationToken(user.email, user.password).let { token ->
                    authenticationManager.authenticate(token)
                }
            }
        } catch (e: IOException) {
            e.stackTrace
        }
        return null
    }

    // 2. attemptAuthentication 에서 성공하면 여기로 넘어온다.
    // JWT 토큰을 만들어서 request 요청한 사용자에게 JWT 를 response 해주면 됨
    override fun successfulAuthentication(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain?, authResult: Authentication) {
        println("successfulAuthentication 실행됨 : 인증이 완료되었다는 뜻임")
        val principalDetails = authResult.principal as PrincipalDetails

        JWT.create()
                .withSubject("tamastudy token")
                .withExpiresAt(Date(System.currentTimeMillis() + (600000 * 10)))
                .withClaim("id", principalDetails.getId())
                .sign(Algorithm.HMAC512("tamastudy"))
                .let { token ->
                    response.addHeader("Authorization", "Bearer $token")
                }
    }
}