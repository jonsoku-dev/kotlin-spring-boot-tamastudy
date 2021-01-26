package com.tamastudy.tama.dto

class AuthenticateDto {
    data class AuthenticateResponse(
            var id: Long? = null,
            var username: String? = null,
            var email: String? = null,
            var roles: String? = null
    )
}