package com.tamastudy.tama.config.auth

import com.tamastudy.tama.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import java.util.function.Consumer


class PrincipalDetails(
        private val user: User
) : UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority> {
        val authorities: MutableCollection<GrantedAuthority> = ArrayList()
        user.getRoleList().forEach(Consumer { r: String? -> authorities.add(GrantedAuthority { r }) })
        return authorities
    }

    fun getUserEntity() = user

    fun getId() = user.id

    override fun getPassword() = user.password

    override fun getUsername() = user.email

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true
}