package com.tamastudy.tama.entity

import com.tamastudy.tama.dto.board.BoardCategoryDto
import javax.persistence.*

@Entity
data class BoardCategory(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "category_id")
        var id: Long? = null,
        var name: String? = null,
)

fun BoardCategory?.convertBoardCategory(boardCategoryDto: BoardCategoryDto): BoardCategory {
    return BoardCategory().apply {
        this.id = boardCategoryDto.id
        this.name = boardCategoryDto.name
    }
}