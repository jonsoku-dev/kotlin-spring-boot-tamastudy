package com.tamastudy.tama.entity

import com.tamastudy.tama.dto.board.BoardDto
import javax.persistence.*

@Entity
data class Board(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "board_id")
        var id: Long? = null,
        var title: String? = null,
        var description: String? = null,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        var user: User? = null,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "category_id")
        var category: BoardCategory? = null
)

fun Board?.convertBoard(boardDto: BoardDto, category: BoardCategory, user: User): Board {
    return Board().apply {
        this.id = boardDto.id
        this.title = boardDto.title
        this.description = boardDto.description
        this.category = category
        this.user = user
    }
}