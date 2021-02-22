package com.tamastudy.tama.controller

import com.tamastudy.tama.dto.User.*
import com.tamastudy.tama.util.PrincipalDetails
import com.tamastudy.tama.service.UserService
import com.tamastudy.tama.util.JwtUtil
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import javax.validation.Valid


@RestController
@RequestMapping("/api/v1/user")
class UserApiController(
        private val jwtUtil: JwtUtil,
        private val authenticationManager: AuthenticationManager,
        private val userService: UserService,
) {
    @PostMapping("/login")
    fun login(
            @RequestBody loginUserRequest: LoginUserRequest,
    ): ResponseEntity<TokenResponse> {
        println(loginUserRequest)
        try {
            authenticationManager.authenticate(
                    UsernamePasswordAuthenticationToken(loginUserRequest.email, loginUserRequest.password)
            )
        } catch (ex: Exception) {
            throw Exception("inavalid username/password")
        }
        val jwt = jwtUtil.generateToken(loginUserRequest.email)
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(TokenResponse(jwt))
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