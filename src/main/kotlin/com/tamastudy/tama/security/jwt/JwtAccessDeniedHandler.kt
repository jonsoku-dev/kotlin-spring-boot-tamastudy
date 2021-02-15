package com.tamastudy.tama.security.jwt

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAccessDeniedHandler : AccessDeniedHandler {

    private val logger: Logger = LoggerFactory.getLogger(JwtAccessDeniedHandler::class.java)

    override fun handle(request: HttpServletRequest?, response: HttpServletResponse?, accessDeniedException: AccessDeniedException?) {
        logger.info("handle Error {}", request)
        response?.sendError(HttpServletResponse.SC_FORBIDDEN).let {
            throw IOException()
        }

    }
}
