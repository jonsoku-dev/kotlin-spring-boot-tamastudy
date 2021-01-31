package com.tamastudy.tama.controller

import com.tamastudy.tama.config.auth.PrincipalDetails
import com.tamastudy.tama.dto.User.CreateUserRequest
import com.tamastudy.tama.dto.User.UserDto
import com.tamastudy.tama.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/user")
class UserApiController(
        private val userService: UserService,
) {
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