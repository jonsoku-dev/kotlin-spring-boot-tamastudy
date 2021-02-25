package com.tamastudy.tama.service.user

import com.tamastudy.tama.dto.*

interface UserService {
    fun findById(id: Long): UserDto
    fun joinUser(userJoinRequestDto: UserJoinRequestDto): UserJoinResponseDto
    fun loginUser(userLoginRequestDto: UserLoginRequestDto): UserLoginResponseDto
    fun refreshToken(token: String, refreshToken: String): UserLoginResponseDto
    fun logoutUser(token: String)
}