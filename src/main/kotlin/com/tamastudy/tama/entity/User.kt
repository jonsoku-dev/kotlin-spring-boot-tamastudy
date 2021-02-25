package com.tamastudy.tama.entity

import com.tamastudy.tama.entity.date.CommonDateEntity
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class User(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "user_id")
        var id: Long? = null,
        @Column(nullable = false)
        var username: String? = null,
        @Column(unique = true)
        var email: String? = null,
        @Column(nullable = false)
        var password: String? = null,
        var roles: String? = null, // ROLE_USER, ROLE_MANAGER, ROLE_ADMIN,
        var provider: String? = null,
        var refreshToken: String? = null,
) : Serializable, CommonDateEntity() {
    companion object {
        @JvmStatic
        private val serialVersionUID: Long = 1
    }

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

    fun changeRefreshToken(refreshToken: String) {
        this.refreshToken = refreshToken
        println("this.refreshToken: ${this.refreshToken}")
    }
}