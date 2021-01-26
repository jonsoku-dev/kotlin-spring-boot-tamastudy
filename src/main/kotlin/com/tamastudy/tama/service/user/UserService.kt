package com.tamastudy.tama.service.user

import com.tamastudy.tama.dto.user.CreateUserRequest
import com.tamastudy.tama.dto.user.CreateUserResponse
import com.tamastudy.tama.dto.user.UserDto
import com.tamastudy.tama.entity.User

interface UserService {
    fun createUser(user: User)
    fun updateUser(user: User)
    fun findAll(): List<UserDto>
    fun findById(id: Long): UserDto
}