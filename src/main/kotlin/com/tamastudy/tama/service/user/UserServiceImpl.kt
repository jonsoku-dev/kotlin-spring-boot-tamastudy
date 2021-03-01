package com.tamastudy.tama.service.user

import com.tamastudy.tama.advice.exception.UserNotFoundException
import com.tamastudy.tama.config.security.JwtTokenProvider
import com.tamastudy.tama.dto.*
import com.tamastudy.tama.entity.User
import com.tamastudy.tama.mapper.UserMapper
import com.tamastudy.tama.repository.user.UserRepository
import org.springframework.data.redis.core.ReactiveStringRedisTemplate
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional(readOnly = true)
class UserServiceImpl(
        private val repository: UserRepository,
        private val bCryptPasswordEncoder: BCryptPasswordEncoder,
        private val jwtTokenProvider: JwtTokenProvider,
        private val redisTemplate: ReactiveStringRedisTemplate,
) : UserService {
    override fun findById(id: Long): UserDto {
        return repository.findById(id).let {
            if (it.isPresent) {
                UserMapper.MAPPER.toDto(it.get())
            } else {
                throw RuntimeException("$id 에 해당하는 유저가 존재하지 않습니다. ")
            }
        }
    }

    @Transactional
    override fun joinUser(userJoinRequestDto: UserJoinRequestDto): UserJoinResponseDto {
        val foundUser = repository.findByEmail(userJoinRequestDto.email)
        if (foundUser != null) {
            throw RuntimeException("${userJoinRequestDto.email} 에 해당하는 계정이 이미 존재합니다.")
        }
        val newUser = User().apply {
            this.username = userJoinRequestDto.username
            this.email = userJoinRequestDto.email
            this.password = bCryptPasswordEncoder.encode(userJoinRequestDto.password)
            this.roles = "ROLE_USER"
        }

        repository.save(newUser)

        return UserJoinResponseDto(
            newUser.id,
            newUser.email,
            newUser.username
        )
    }

    @Transactional
    override fun loginUser(userLoginRequestDto: UserLoginRequestDto): UserLoginResponseDto {
        val user = repository.findByEmail(userLoginRequestDto.email)
                ?: throw RuntimeException("${userLoginRequestDto.email} 에 해당하는 계정이 존재하지 않습니다")
        if (!bCryptPasswordEncoder.matches(userLoginRequestDto.password, user.password)) throw RuntimeException("비밀번호를 확인해주세요.")
        user.refreshToken = jwtTokenProvider.createRefreshToken()
        return UserLoginResponseDto(
            user.id,
            jwtTokenProvider.createToken(user.email)!!,
            user.refreshToken
        )
    }

    @Transactional
    override fun refreshToken(token: String, refreshToken: String): UserLoginResponseDto {
        // 아직 만료되지 않은 토큰으로는 refresh 할 수 없음
        if (!jwtTokenProvider.validateTokenExceptExpiration(token)) throw org.springframework.security.access.AccessDeniedException("")
        println("token: $token")
        println("jwtTokenProvider.getUserEmail(token): ${jwtTokenProvider.getUserEmail(token)}")
        val user: User = repository.findByEmail(jwtTokenProvider.getUserEmail(token)) ?: throw UserNotFoundException()
        if (!jwtTokenProvider.validateToken(user.refreshToken) || refreshToken != user.refreshToken) throw org.springframework.security.access.AccessDeniedException("")
        user.refreshToken = jwtTokenProvider.createRefreshToken()
        return UserLoginResponseDto(user.id, jwtTokenProvider.createToken(user.email)!!, user.refreshToken)
    }

    @Transactional
    override fun logoutUser(token: String) {
        redisTemplate.opsForValue().set("token:$token", "v", jwtTokenProvider.getRemainingSeconds(token))
        val foundUser = repository.findByEmail(jwtTokenProvider.getUserEmail(token))
                ?: throw RuntimeException("유저를 찾을 수 없습니다. ")
        foundUser.refreshToken = "invalidate"
        println(foundUser)
    }
}