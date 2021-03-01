package com.tamastudy.tama.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable
import java.time.LocalDateTime

data class UserDto(
    @JsonProperty(value = "userId")
    var id: Long = 0,
    var username: String = "",
    var email: String = "",
    @JsonIgnore
    var password: String = "",
    var roles: String = "", // ROLE_USER, ROLE_MANAGER, ROLE_ADMIN,
    var refreshToken: String = "",
    var createdAt: LocalDateTime? = null,
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

data class UserLoginResponseDto(
    var id: Long,
    var token: String,
    var refreshToken: String,
)

data class UserJoinRequestDto(
    var email: String,
    var password: String,
    var username: String,
)

data class UserJoinResponseDto(
    @JsonProperty(value = "userId")
    var id: Long,
    var username: String,
    var email: String,
)
