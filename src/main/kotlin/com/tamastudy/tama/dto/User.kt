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

    data class UserInfo(
            var id: Long? = null,
            var username: String? = null,
            var email: String? = null,
            var roles: String? = null// ROLE_USER, ROLE_MANAGER, ROLE_ADMIN
    )

    data class CreateUserRequest(
            @field:Length(min = 2, max = 10)
            var username: String,
            @field:NotBlank
            @field:Email
            var email: String,
            @field:Length(min = 4, max = 20)
            var password: String
    )

    data class UpdateUserRequest(
            @field:Length(min = 2, max = 10)
            var username: String,
            @field:Length(min = 4, max = 20)
            var password: String
    )
}