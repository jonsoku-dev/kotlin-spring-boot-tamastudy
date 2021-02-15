package com.tamastudy.tama.entity

import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Comment(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,
        var text: String? = null,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        var user: User? = null,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "board_id")
        var board: Board? = null,
        @Column(updatable = false)
        var createdAt: LocalDateTime? = LocalDateTime.now(),
        var updatedAt: LocalDateTime? = LocalDateTime.now()
) : Serializable {
    companion object {
        @JvmStatic
        private val serialVersionUID: Long = 1
    }
}