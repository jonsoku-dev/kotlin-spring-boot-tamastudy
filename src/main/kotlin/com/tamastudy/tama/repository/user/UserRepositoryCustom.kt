package com.tamastudy.tama.repository.user

import com.tamastudy.tama.entity.User


interface UserRepositoryCustom {
    fun findForJoinByEmail(email: String): User?
    fun findByEmail(email: String): User?
}