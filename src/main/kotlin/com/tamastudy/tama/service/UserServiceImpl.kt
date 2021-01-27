package com.tamastudy.tama.service

import com.tamastudy.tama.dto.UserDto.UserInfo
import com.tamastudy.tama.entity.User
import com.tamastudy.tama.mapper.UserMapper
import com.tamastudy.tama.repository.UserRepository
import javassist.NotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

@Service
class UserServiceImpl(
        private val repository: UserRepository,
        private val bCryptPasswordEncoder: BCryptPasswordEncoder
) : UserService {
    override fun createUser(user: User): UserInfo {
        checkExistUser(user.email.orEmpty())

        val newUser = User().apply {
            this.username = user.username
            this.email = user.email
            this.password = bCryptPasswordEncoder.encode(user.password)
            this.roles = "ROLE_USER"
        }

        return UserMapper.MAPPER.toDto(repository.save(newUser))
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
        return UserMapper.MAPPER.toDtos(repository.findAll())
    }

    override fun findById(id: Long): UserInfo {
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