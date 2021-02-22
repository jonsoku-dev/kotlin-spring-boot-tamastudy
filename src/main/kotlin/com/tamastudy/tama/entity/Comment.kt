package com.tamastudy.tama.entity

import com.fasterxml.jackson.annotation.JsonBackReference
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

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "super_comment_id")
        var superComment: Comment? = null,

        @OneToMany(mappedBy = "superComment", cascade = [CascadeType.ALL])
        var subComment: MutableList<Comment>? = mutableListOf<Comment>(),

        var level: Int? = null,

        var isLive: Boolean? = true,

        @Column(updatable = false)
        var createdAt: LocalDateTime? = LocalDateTime.now(),
        var updatedAt: LocalDateTime? = LocalDateTime.now(),
) : Serializable {
    companion object {
        @JvmStatic
        private val serialVersionUID: Long = 1
    }

    fun addSubComment(subComment: Comment) {
        this.subComment?.add(subComment)
        subComment.superComment = subComment
    }
}