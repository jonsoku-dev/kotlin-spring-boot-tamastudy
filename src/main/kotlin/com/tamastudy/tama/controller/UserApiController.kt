package com.tamastudy.tama.controller

import com.tamastudy.tama.security.auth.PrincipalDetails
import com.tamastudy.tama.dto.User.*
import com.tamastudy.tama.security.jwt.JwtFilter
import com.tamastudy.tama.security.jwt.TokenProvider
import com.tamastudy.tama.service.UserService
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/user")
class UserApiController(
        private val tokenProvider: TokenProvider,
        private val authenticationManagerBuilder: AuthenticationManagerBuilder,
        private val userService: UserService,
) {
    @PostMapping("/login")
    fun login(@RequestBody loginUserRequest: LoginUserRequest): ResponseEntity<TokenResponse> {
        val authenticationToken = UsernamePasswordAuthenticationToken(loginUserRequest.email, loginUserRequest.password)
        val authentication: Authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken)
        SecurityContextHolder.getContext().authentication = authentication
        val jwt = tokenProvider.createToken(authentication)!!
        val httpHeaders = HttpHeaders()
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer $jwt")
        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(httpHeaders)
                .body(TokenResponse(jwt))
    }

    @PostMapping("/join")
    fun join(@Valid @RequestBody createUserRequest: CreateUserRequest): ResponseEntity<Unit> {
        userService.createUser(createUserRequest)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/authenticate")
    fun authenticate(): ResponseEntity<UserDto> {
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