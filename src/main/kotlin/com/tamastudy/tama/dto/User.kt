package com.tamastudy.tama.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.validator.constraints.Length
import java.time.Instant
import java.time.LocalDateTime
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

class User {
    data class UserDto(
            var id: Long? = null,
            var username: String? = null,
            var email: String? = null,
            @JsonIgnore
            var password: String? = null,
            @JsonIgnore
            var roles: String? = null,
            @JsonIgnore
            var createdAt: LocalDateTime? = null,
            @JsonIgnore
            var updatedAt: LocalDateTime? = null,
    )

    data class LoginUserRequest(
            var email: String,
            var password: String
    )

    data class CreateUserRequest(
            @field:NotBlank
            @field:Length(max = 10)
            var username: String,
            @field:NotBlank
            @field:Email
            var email: String,
            @field:NotBlank
            @field:Length(max = 20)
            var password: String
    )

    data class UpdateUserRequest(
            @field:Length(max = 10)
            var username: String,
            @field:Length(max = 20)
            var password: String
    )

    data class TokenResponse(
            var token: String
    )
}