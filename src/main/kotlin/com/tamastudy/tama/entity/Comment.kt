package com.tamastudy.tama.entity

import com.tamastudy.tama.entity.date.CommonDateEntity
import java.io.Serializable
import javax.persistence.*

@Entity
data class Comment(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    var text: String = "",
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User? = User(),
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    var board: Board? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "super_comment_id")
    var superComment: Comment? = null,

    @OneToMany(mappedBy = "superComment", cascade = [CascadeType.ALL])
    var subComment: MutableList<Comment>? = mutableListOf<Comment>(),

    var level: Int = 0,

    var isLive: Boolean = true,
) : Serializable, CommonDateEntity() {
    companion object {
        @JvmStatic
        private val serialVersionUID: Long = 1
    }

    fun addSubComment(subComment: Comment) {
        this.subComment?.add(subComment)
        subComment.superComment = subComment
    }
}