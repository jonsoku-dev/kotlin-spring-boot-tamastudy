package com.tamastudy.tama.util

import com.tamastudy.tama.entity.User
import com.tamastudy.tama.mapper.UserMapper
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import java.util.function.Consumer


class PrincipalDetails(private var user: User) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> {
        val authorities: MutableCollection<GrantedAuthority> = ArrayList()
        user.getRoleList().forEach(Consumer { r: String? -> authorities.add(GrantedAuthority { r }) })
        return authorities
    }

    fun getUserEntity() = user

    fun getUserDto() = UserMapper.MAPPER.toDto(user)

    fun getId() = user.id

    override fun getPassword() = user.password

    override fun getUsername() = user.email

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true
}