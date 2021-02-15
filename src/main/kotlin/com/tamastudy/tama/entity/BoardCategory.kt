package com.tamastudy.tama.entity

import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class BoardCategory(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "category_id")
        var id: Long? = null,
        var name: String? = null,
        @Column(updatable = false)
        var createdAt: LocalDateTime? = LocalDateTime.now(),
        var updatedAt: LocalDateTime? = LocalDateTime.now(),
): Serializable {
        companion object {
                @JvmStatic
                private val serialVersionUID: Long = 1
        }
}