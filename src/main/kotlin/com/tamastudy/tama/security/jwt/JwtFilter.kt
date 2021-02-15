package com.tamastudy.tama.security.jwt

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.util.StringUtils
import org.springframework.web.filter.GenericFilterBean
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

class JwtFilter(private val tokenProvider: TokenProvider) : GenericFilterBean() {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(JwtFilter::class.java)
        const val AUTHORIZATION_HEADER = "Authorization"
    }

    private fun resolveToken(request: HttpServletRequest): String? {
        if (request.getHeader(AUTHORIZATION_HEADER) == "x") {
            return ""
        }
        return request.getHeader(AUTHORIZATION_HEADER)?.substring(7)
    }

    override fun doFilter(servletRequest: ServletRequest, servletResponse: ServletResponse, filterChain: FilterChain) {
        val httpServletRequest = servletRequest as HttpServletRequest
        val jwt = resolveToken(httpServletRequest)
        val requestURI = httpServletRequest.requestURI
        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            val authentication: Authentication? = tokenProvider.getAuthentication(jwt)
            SecurityContextHolder.getContext().authentication = authentication
            Companion.logger.debug("Security Context 에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication?.name, requestURI)
        } else {
            SecurityContextHolder.getContext().authentication = null
            Companion.logger.debug("유효한 JWT 토큰이 없습니다, uri: {}", requestURI)
        }
        filterChain.doFilter(servletRequest, servletResponse)
    }
}