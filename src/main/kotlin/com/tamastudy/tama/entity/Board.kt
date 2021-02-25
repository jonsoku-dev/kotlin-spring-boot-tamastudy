package com.tamastudy.tama.entity

import com.tamastudy.tama.entity.date.CommonDateEntity
import java.io.Serializable
import javax.persistence.*

@Entity
data class Board(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "board_id")
        var id: Long? = null,
        var title: String? = null,
        @Column(name = "description", columnDefinition = "TEXT")
        var description: String? = null,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        var user: User? = null,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "category_id")
        var category: BoardCategory? = null,
) : Serializable, CommonDateEntity() {
    companion object {
        @JvmStatic
        private val serialVersionUID: Long = 1
    }
}