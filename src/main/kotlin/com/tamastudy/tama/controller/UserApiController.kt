package com.tamastudy.tama.controller

import com.tamastudy.tama.dto.UserDto.CreateUserRequest
import com.tamastudy.tama.mapper.UserMapper
import com.tamastudy.tama.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping(value = ["/api/v1"])
class UserApiController(
        private val userMapper: UserMapper,
        private val userService: UserService,
) {
    @PostMapping(value = ["/join"])
    fun join(@Valid @RequestBody createUserRequest: CreateUserRequest): ResponseEntity<Unit> {
        userMapper.CreateUserRequestToEntity(createUserRequest).also {
            userService.createUser(it)
        }
        return ResponseEntity.noContent().build()
    }
}