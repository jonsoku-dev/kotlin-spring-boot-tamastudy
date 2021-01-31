package com.tamastudy.tama.service

import com.tamastudy.tama.dto.User.*
import com.tamastudy.tama.entity.ROLES
import com.tamastudy.tama.entity.User
import com.tamastudy.tama.mapper.UserMapper
import com.tamastudy.tama.repository.UserRepository
import javassist.NotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
        private val repository: UserRepository,
        private val bCryptPasswordEncoder: BCryptPasswordEncoder
) : UserService {
    override fun createUser(createUserRequest: CreateUserRequest): UserDto {
        checkExistUser(createUserRequest.email)
        return User().apply {
            this.username = createUserRequest.username
            this.email = createUserRequest.email
            this.password = bCryptPasswordEncoder.encode(createUserRequest.password)
            this.roles = ROLES.ROLE_USER
        }.also {
            println("createUser: $it")
        }.let { user ->
            UserMapper.MAPPER.toDto(repository.save(user))
        }
    }

    override fun updateUser(id: Long, updateUserRequest: UpdateUserRequest): UserDto {
        return findUserEntityById(id).apply {
            this.username = updateUserRequest.username
            this.password = bCryptPasswordEncoder.encode(updateUserRequest.password)
        }.also {
            println("updateUser: $it")
        }.let { user ->
            UserMapper.MAPPER.toDto(repository.save(repository.save(user)))
        }
    }

    override fun findAll(): List<UserDto> {
        return UserMapper.MAPPER.toDtos(repository.findAll())
    }

    override fun findById(id: Long): UserDto {
        return UserMapper.MAPPER.toDto(findUserEntityById(id))
    }

    private fun checkExistUser(email: String) {
        return repository.findByEmail(email).let {
            if (it.isPresent) {
                throw IllegalArgumentException("$email 은 중복된 이메일입니다.")
            }
        }
    }

    private fun findUserEntityById(id: Long): User {
        return repository.findById(id).let {
            if (it.isPresent) {
                it.get()
            } else {
                throw NotFoundException("$id 에 해당하는 유저가 존재하지 않습니다. ")
            }
        }
    }
}