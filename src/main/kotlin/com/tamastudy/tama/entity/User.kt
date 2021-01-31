package com.tamastudy.tama.entity

import java.time.LocalDateTime
import javax.persistence.*

enum class ROLES {
    ROLE_USER,
    ROLE_MANAGER,
    ROLE_ADMIN
}

@Entity
data class User(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "user_id")
        var id: Long? = null,
        var username: String? = null,
        var email: String? = null,
        var password: String? = null,
        var roles: ROLES? = null, // ROLE_USER, ROLE_MANAGER, ROLE_ADMIN
        @Column(updatable = false)
        var createdAt: LocalDateTime? = LocalDateTime.now(),
        var updatedAt: LocalDateTime? = LocalDateTime.now()
)  {
    fun getRoleList(): MutableList<String> {
        val list = mutableListOf<String>()
        if (this.roles?.isNotEmpty() == true) {
            this.roles?.split(",").let { roleList ->
                roleList?.forEach { role ->
                    list.add(role)
                }
                return list
            }
        }
        return list
    }
}