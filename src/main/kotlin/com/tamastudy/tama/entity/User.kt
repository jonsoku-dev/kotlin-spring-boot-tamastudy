package com.tamastudy.tama.entity

import javax.persistence.*

@Entity
data class User(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "user_id")
        var id: Long? = null,
        var username: String? = null,
        var email: String? = null,
        var password: String? = null,
        var roles: String? = null, // ROLE_USER, ROLE_MANAGER, ROLE_ADMIN
) {
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