package com.tamastudy.tama.controller

import com.tamastudy.tama.dto.UserDto.CreateUserRequest
import com.tamastudy.tama.dto.UserDto.UserInfo
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
        UserMapper.MAPPER.CreateUserRequestToEntity(createUserRequest).also {
            userService.createUser(it)
        }
        return ResponseEntity.noContent().build()
    }

    @GetMapping
    fun getUsers(): ResponseEntity<List<UserInfo>> {
        return ResponseEntity.ok().body(userService.findAll())
    }

    @GetMapping("/{userId}")
    fun getUser(@PathVariable userId: Long): ResponseEntity<UserInfo> {
        return ResponseEntity.ok().body(userService.findById(userId))
    }
}