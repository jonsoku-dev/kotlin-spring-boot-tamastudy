package com.tamastudy.tama.entity

import com.tamastudy.tama.dto.user.UserDto
import javax.persistence.*

@Entity
data class User(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "user_id")
        var id: Long? = 0,
        var username: String? = null,
        var email: String? = null,
        var password: String? = null,
        var roles: String? = null, // ROLE_USER, ROLE_MANAGER, ROLE_ADMIN

//        @OneToMany(mappedBy = "user")
//        var boards: List<Board>? = null

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

fun User?.convertUser(userDto: UserDto): User {
    return User().apply {
        this.id = userDto.id
        this.username = userDto.username
        this.email = userDto.email
        this.roles = userDto.roles
    }
}