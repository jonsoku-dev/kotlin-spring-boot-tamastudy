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
        private val mapper: UserMapper,
        private val repository: UserRepository,
        private val bCryptPasswordEncoder: BCryptPasswordEncoder
) : UserService {
    override fun createUser(user: User) {
        User().apply {
            this.username = user.username
            this.email = user.email
            this.password = bCryptPasswordEncoder.encode(user.password)
            this.roles = "ROLE_USER"
        }.also {
            println("createUser: $it")
        }.let {
            repository.save(it)
        }
    }

    override fun updateUser(id: Long, user: User) {
        findUserEntityById(id).apply {
            this.username = user.username
            this.email = user.email
            this.roles = user.roles
        }.also {
            println("updateUser: $it")
        }.let {
            repository.save(it)
        }
    }

    override fun findAll(): List<UserInfo> {
        return mapper.toDtos(repository.findAll())
    }

    override fun findById(id: Long): UserInfo {
        return mapper.toDto(findUserEntityById(id))
    }

    private fun findUserEntityById(id: Long): User {
        return repository.findByIdOrNull(id) ?: throw NotFoundException("$id 에 해당하는 유저가 존재하지 않습니다. ")
    }
}