package com.tamastudy.tama.dto.board

import com.tamastudy.tama.dto.user.UserDto
import com.tamastudy.tama.entity.Board
import com.tamastudy.tama.entity.BoardCategory
import com.tamastudy.tama.entity.User

data class BoardCategoryDto(
        var id: Long? = null,
        var name: String? = null,
)

fun BoardCategoryDto?.toBoardCategory(): BoardCategory {
    return BoardCategory(
            id = this?.id,
            name = this?.name
    )
}

fun BoardCategoryDto?.convertBoardCategoryDto(boardCategory: BoardCategory): BoardCategoryDto {
    return BoardCategoryDto().apply {
        this.id = boardCategory.id
        this.name = boardCategory.name
    }
}