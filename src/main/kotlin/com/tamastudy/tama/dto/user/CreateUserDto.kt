package com.tamastudy.tama.dto.user

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class CreateUserRequest(
        @field:Length(min = 2, max = 10)
        var username: String? = null,
        @field:NotBlank
        @field:Email
        var email: String? = null,
        @field:Length(min = 4, max = 20)
        var password: String? = null
)

data class CreateUserResponse(
        var token: String? = null
)