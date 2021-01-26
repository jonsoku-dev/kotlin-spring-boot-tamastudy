package com.tamastudy.tama.service.user

import com.tamastudy.tama.dto.user.UserDto
import com.tamastudy.tama.dto.user.convertUserDto
import com.tamastudy.tama.entity.User
import com.tamastudy.tama.repository.user.UserRepository
import javassist.NotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
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
            println("HERE=======HERE=======HERE=======HERE=======HERE=======")
            println(it)
            userRepository.save(it)
        }
    }

    override fun updateUser(user: User) {
        TODO("Not yet implemented")
    }

    override fun findAll(): List<UserDto> {
        TODO("Not yet implemented")
    }

    override fun findById(id: Long): UserDto {
        return userRepository.findByIdOrNull(id)?.let {
            UserDto().convertUserDto(it)
        } ?: throw NotFoundException("$id 에 해당하는 유저가 존재하지 않습니다. ")
    }
}