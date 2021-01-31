package com.tamastudy.tama.controller

import com.tamastudy.tama.dto.User.CreateUserRequest
import com.tamastudy.tama.dto.User.UserDto
import com.tamastudy.tama.mapper.UserMapper
import com.tamastudy.tama.service.UserService
import org.springframework.http.ResponseEntity
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

    @GetMapping
    fun getUsers(): ResponseEntity<List<UserDto>> {
        return ResponseEntity.ok().body(userService.findAll())
    }

    @GetMapping("/{userId}")
    fun getUser(@PathVariable userId: Long): ResponseEntity<UserDto> {
        return ResponseEntity.ok().body(userService.findById(userId))
    }
}