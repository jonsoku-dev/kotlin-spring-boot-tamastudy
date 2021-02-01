package com.tamastudy.tama.security.auth

import com.tamastudy.tama.repository.UserRepository
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

// http://localhost:8080/login 요청이 올때 동작을 한다 !
@Service
class PrincipalDetailsService(
        private val userRepository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(email: String): UserDetails {
        println("PrincipalDetailsService 의 loadUserByUsername()")
        return userRepository.findByEmail(email).let { userEntity ->
            if (userEntity.isPresent) {
                PrincipalDetails(userEntity.get())
            } else {
                throw InternalAuthenticationServiceException("email 로 유저를 찾을 수 없습니다.")
            }
        }
    }
}