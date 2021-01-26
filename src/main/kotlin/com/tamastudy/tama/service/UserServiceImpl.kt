package com.tamastudy.tama.service

import com.tamastudy.tama.dto.UserDto.UserInfo
import com.tamastudy.tama.entity.User
import com.tamastudy.tama.mapper.UserMapper
import com.tamastudy.tama.repository.UserRepository
import javassist.NotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
        private val userMapper: UserMapper,
        private val userRepository: UserRepository,
        private val bCryptPasswordEncoder: BCryptPasswordEncoder
) : UserService {
    override fun createUser(user: User) {
        User().apply {
            this.username = user.username
            this.email = user.email
            this.password = bCryptPasswordEncoder.encode(user.password)
            this.roles = "ROLE_USER"
        }.let {
            userRepository.save(it)
        }
    }

    override fun updateUser(user: User) {
        TODO("Not yet implemented")
    }

    override fun findAll(): List<UserInfo> {
        TODO("Not yet implemented")
    }

    override fun findById(id: Long): UserInfo {
        return userRepository.findByIdOrNull(id)?.let {
            userMapper.toDto(it)
        } ?: throw NotFoundException("$id 에 해당하는 유저가 존재하지 않습니다. ")
    }
}