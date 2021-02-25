package com.tamastudy.tama.model

import org.springframework.security.core.GrantedAuthority

import org.springframework.security.core.userdetails.UserDetails


class CustomUserDetails(private val username: String, private val password: String, var authorities: List<GrantedAuthority>) : UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority> {
        return authorities
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return username
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}