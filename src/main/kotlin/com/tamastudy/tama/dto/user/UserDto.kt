package com.tamastudy.tama.dto.user

import com.tamastudy.tama.entity.User

data class UserDto(
        var id: Long? = null,
        var username: String? = null,
        var email: String? = null,
        var roles: String? = null// ROLE_USER, ROLE_MANAGER, ROLE_ADMIN
)

fun UserDto?.convertUserDto(user: User): UserDto {
    return UserDto().apply {
        this.id = user.id
        this.username = user.username
        this.email = user.email
        this.roles = user.roles
    }
}
