package com.tamastudy.tama.controller

import com.tamastudy.tama.config.auth.PrincipalDetails
import com.tamastudy.tama.dto.User.UserDto
import com.tamastudy.tama.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping(value = ["/api/v1"])
class AuthApiController(
        private val userService: UserService,
) {
    @GetMapping(value = ["authenticate"])
    fun authenticate(): ResponseEntity<UserDto> {
        val id = (SecurityContextHolder.getContext().authentication.principal as PrincipalDetails).getId()!!
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.findById(id))
    }
}