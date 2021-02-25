package com.tamastudy.tama.service.user

import com.tamastudy.tama.advice.exception.NotSameRefreshTokenException
import com.tamastudy.tama.advice.exception.UserNotFoundException
import com.tamastudy.tama.advice.exception.ValidateRefreshTokenException
import com.tamastudy.tama.config.security.JwtTokenProvider
import com.tamastudy.tama.dto.*
import com.tamastudy.tama.entity.User
import com.tamastudy.tama.mapper.UserMapper
import com.tamastudy.tama.repository.user.UserRepository
import org.springframework.data.redis.core.ReactiveStringRedisTemplate
import org.springframework.security.access.AccessDeniedException
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
        val foundUser = repository.findByEmail(userJoinRequestDto.email!!)
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

        return UserJoinResponseDto().apply {
            this.id = newUser.id
            this.email = newUser.email
            this.username = newUser.username
        }
    }

    @Transactional
    override fun loginUser(userLoginRequestDto: UserLoginRequestDto): UserLoginResponseDto {
        val user = repository.findByEmail(userLoginRequestDto.email)
                ?: throw RuntimeException("${userLoginRequestDto.email} 에 해당하는 계정이 존재하지 않습니다")
        if (!bCryptPasswordEncoder.matches(userLoginRequestDto.password, user.password)) throw RuntimeException("비밀번호를 확인해주세요.")
        user.refreshToken = jwtTokenProvider.createRefreshToken()
        return UserLoginResponseDto().apply {
            this.id = user.id
            this.token = jwtTokenProvider.createToken(user.email!!)
            this.refreshToken = user.refreshToken
        }
    }


    @Transactional
    override fun refreshToken(token: String, refreshToken: String): UserLoginResponseDto {
        // 만료일자 지나면 true 반환
        println("wtTokenProvider.validateTokenExceptExpiration(token): ${jwtTokenProvider.validateTokenExceptExpiration(token)}")
        if (!jwtTokenProvider.validateTokenExceptExpiration(token)) {
            throw AccessDeniedException("토큰이 정상적입니다~~~")
        }
        val foundUser = repository.findByEmail(jwtTokenProvider.getUserEmail(token))
                ?: throw UserNotFoundException("유저를 찾을 수 없습니다.")

        if(!jwtTokenProvider.validateToken(foundUser.refreshToken)) {
            throw ValidateRefreshTokenException("")
        }
        if (refreshToken != foundUser.refreshToken) {
            throw NotSameRefreshTokenException("")
        }
        // refresh token 이 같을 때 저장한다!
        foundUser.refreshToken = jwtTokenProvider.createRefreshToken()
        return UserLoginResponseDto().apply {
            this.id = foundUser.id
            this.token = jwtTokenProvider.createToken(foundUser.email!!)
            this.refreshToken = foundUser.refreshToken
        }
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