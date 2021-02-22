package com.tamastudy.tama.service

import com.tamastudy.tama.repository.UserRepository
import com.tamastudy.tama.util.PrincipalDetails
import javassist.NotFoundException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
        private val userRepository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepository.findByEmail(email) ?: throw NotFoundException("유저를 찾을 수 없습니다.")
        return PrincipalDetails(user)
    }
}