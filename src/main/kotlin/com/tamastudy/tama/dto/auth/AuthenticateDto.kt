package com.tamastudy.tama.dto.auth

data class AuthenticateResponse(
        var id: Long? = null,
        var username: String? = null,
        var email: String? = null,
        var roles: String? = null
)