package com.tamastudy.tama.service

import com.tamastudy.tama.dto.User.*
import com.tamastudy.tama.entity.User

interface UserService {
    fun createUser(createUserRequest: CreateUserRequest): UserDto
    fun updateUser(id: Long, updateUserRequest: UpdateUserRequest): UserDto
    fun findAll(): List<UserDto>
    fun findById(id: Long): UserDto
}