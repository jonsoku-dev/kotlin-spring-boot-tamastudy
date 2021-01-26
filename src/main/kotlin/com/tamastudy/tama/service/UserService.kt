package com.tamastudy.tama.service

import com.tamastudy.tama.dto.UserDto.UserInfo
import com.tamastudy.tama.entity.User

interface UserService {
    fun createUser(user: User)
    fun updateUser(id: Long, user: User)
    fun findAll(): List<UserInfo>
    fun findById(id: Long): UserInfo
}