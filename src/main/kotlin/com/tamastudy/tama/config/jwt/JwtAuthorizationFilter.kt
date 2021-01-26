package com.tamastudy.tama.config.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.tamastudy.tama.config.auth.PrincipalDetails
import com.tamastudy.tama.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


// 인증요청이 있을 때 동작하는 것이 아니라
// 시큐리티가 filter 를 가지고 있는데 그 filter 중에 BasicAuthenticationFilter 라는 것이 있음.
// 권한이나 인증이 필요한 특정 주소를 요청 했을 때, 위 filter 를 무조건 타게 되어있음.
// 만약에 권한이나 인증이 필요한 주소가 아니라면 이 filter 를 타지 않음.
class JwtAuthorizationFilter(
        authenticationManager: AuthenticationManager,
        private val userRepository: UserRepository
) : BasicAuthenticationFilter(authenticationManager) {

    // 인증이나 권한이 필요한 주소요청이 있을 때 해당 필터를 타게 된다.
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        println("인증이나 권한이 필요한 주소 요청이 됨!")
        val jwtHeader = request.getHeader("Authorization")

        // header 가 있는지 확인
        if (jwtHeader == null || !jwtHeader.startsWith("Bearer")) {
            chain.doFilter(request, response)
            return
        }
        // JWT 토큰을 검증해서 정상적인 사용자인지 확인
        val jwtToken = request.getHeader("Authorization").replace("Bearer ", "")
        JWT.require(Algorithm.HMAC512("tamastudy")).build().verify(jwtToken).getClaim("id").asLong().let { id ->
            userRepository.findByIdOrNull(id)?.let { it ->
                val principalDetails = PrincipalDetails(it)
                // Jwt 토큰 서명을 통해서 서명이 정상이면 Authentication 객체를 만들어 준다.
                val authentication = UsernamePasswordAuthenticationToken(principalDetails, "", principalDetails.authorities)
                // 강제로 시큐리티의 세션에 접근하여 Authentication 객체를 저장한다.
                SecurityContextHolder.getContext().authentication = authentication
                chain.doFilter(request, response)
            } ?: run {
                chain.doFilter(request, response)
                return
            }
        }
    }
}
