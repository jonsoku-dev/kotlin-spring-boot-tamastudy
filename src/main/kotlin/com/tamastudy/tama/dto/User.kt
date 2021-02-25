package com.tamastudy.tama.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import org.hibernate.validator.constraints.Length
import java.io.Serializable
import java.time.LocalDateTime
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class UserDto(
        @JsonProperty(value = "userId")
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
) : Serializable {
    companion object {
        @JvmStatic
        private val serialVersionUID: Long = 1
    }
}

data class UserLoginRequestDto(
        var email: String,
        var password: String,
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
        var password: String,
)

data class UpdateUserRequest(
        @field:Length(max = 10)
        var username: String,
        @field:Length(max = 20)
        var password: String,
)

data class TokenResponse(
        var token: String,
)

data class UserLoginResponseDto(
        var id: Long? = null,
        var token: String? = null,
        var refreshToken: String? = null,
)

data class UserJoinRequestDto(
        var email: String? = null,
        var password: String? = null,
        var username: String? = null,
)

data class UserJoinResponseDto(
        @JsonProperty(value = "userId")
        var id: Long? = null,
        var username: String? = null,
        var email: String? = null,
)
