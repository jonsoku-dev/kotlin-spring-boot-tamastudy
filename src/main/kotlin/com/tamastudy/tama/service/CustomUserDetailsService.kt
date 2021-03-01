package com.tamastudy.tama.service

import com.tamastudy.tama.repository.user.UserRepository
import com.tamastudy.tama.util.PrincipalDetails
import javassist.NotFoundException
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cache.annotation.Cacheable
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
@Qualifier("customUserDetailsService")
class CustomUserDetailsService(
        private val userRepository: UserRepository,
) : UserDetailsService {
    @Cacheable(value = ["user"], key = "#email", unless = "#result == null")
    override fun loadUserByUsername(email: String): UserDetails {
        println("loadUserBy email: $email")
        val user = userRepository.findByEmail(email) ?: throw NotFoundException("유저를 찾을 수 없습니다.")
        return PrincipalDetails(user)
    }
}