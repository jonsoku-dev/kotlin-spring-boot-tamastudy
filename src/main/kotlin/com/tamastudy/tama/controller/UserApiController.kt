package com.tamastudy.tama.controller

import com.tamastudy.tama.dto.User.*
import com.tamastudy.tama.security.auth.PrincipalDetails
import com.tamastudy.tama.security.jwt.TokenProvider
import com.tamastudy.tama.service.UserService
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler
import org.springframework.web.bind.annotation.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid


@RestController
@RequestMapping("/api/v1/user")
class UserApiController(
        private val tokenProvider: TokenProvider,
        private val authenticationManagerBuilder: AuthenticationManagerBuilder,
        private val userService: UserService,
) {
    @PostMapping("/login")
    fun login(
            @RequestBody loginUserRequest: LoginUserRequest,
            response: HttpServletResponse
    ): ResponseEntity<TokenResponse> {
        val authenticationToken = UsernamePasswordAuthenticationToken(loginUserRequest.email, loginUserRequest.password)
        val authentication: Authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken)
        SecurityContextHolder.getContext().authentication = authentication
        val jwt = tokenProvider.createToken(authentication)!!
        val httpHeaders = HttpHeaders()
//
//        val cookie = Cookie("jwt", jwt)
//        cookie.maxAge = 7 * 24 * 60 * 60
//        cookie.secure = false
//        cookie.isHttpOnly = false
//        cookie.path = "/"

//        response.addCookie(cookie);

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(httpHeaders)
                .body(TokenResponse(jwt))
    }

    @GetMapping("/logout")
    fun logout(request: HttpServletRequest, response: HttpServletResponse): ResponseEntity<Unit> {
        var auth = SecurityContextHolder.getContext().authentication
        if (auth != null) {
            val cookie = Cookie("jwt", null)
            cookie.maxAge = 0
            cookie.secure = false
            cookie.isHttpOnly = false
            cookie.path = "/"
            response.addCookie(cookie);
            SecurityContextLogoutHandler().logout(request, response, auth)
        }
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/join")
    fun join(@Valid @RequestBody createUserRequest: CreateUserRequest): ResponseEntity<Unit> {
        userService.createUser(createUserRequest)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/authenticate")
    fun authenticate(): ResponseEntity<UserDto> {
        if (SecurityContextHolder.getContext().authentication.principal == "anonymousUser") {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .build()
        }
        val userDto = (SecurityContextHolder.getContext().authentication.principal as PrincipalDetails).getUserDto()
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userDto)
    }

    @GetMapping("/")
    fun getUsers(): ResponseEntity<List<UserDto>> {
        val userDtos = userService.findAll()
        return ResponseEntity.status(HttpStatus.OK).body(userDtos)
    }

    @GetMapping("/{userId}")
    fun getUser(@PathVariable userId: Long): ResponseEntity<UserDto> {
        val userDto = userService.findById(userId)
        return ResponseEntity.status(HttpStatus.OK).body(userDto)
    }
}