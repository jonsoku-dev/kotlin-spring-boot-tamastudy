package com.tamastudy.tama.dto

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

class UserDto {
    data class UserInfo(
            var id: Long? = null,
            var username: String? = null,
            var email: String? = null,
            var roles: String? = null// ROLE_USER, ROLE_MANAGER, ROLE_ADMIN
    )

    data class CreateUserRequest(
            @field:Length(min = 2, max = 10)
            var username: String? = null,
            @field:NotBlank
            @field:Email
            var email: String? = null,
            @field:Length(min = 4, max = 20)
            var password: String? = null
    )
}