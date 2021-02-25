package com.tamastudy.tama.repository.user

import com.tamastudy.tama.entity.User
import org.springframework.data.jpa.repository.JpaRepository


interface UserRepository : JpaRepository<User, Long>, UserRepositoryCustom {
}