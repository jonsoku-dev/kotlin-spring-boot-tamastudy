package com.tamastudy.tama.dto

import com.querydsl.core.annotations.QueryProjection
import com.tamastudy.tama.dto.BoardCategory.BoardCategoryDto
import com.tamastudy.tama.dto.Comment.CommentDto
import com.tamastudy.tama.dto.User.UserDto
import org.hibernate.validator.constraints.Length
import java.io.Serializable
import java.time.LocalDateTime
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class Board {
    data class BoardDto(
            var id: Long? = null,
            var title: String? = null,
            var description: String? = null,
            var user: UserDto? = null,
            var category: BoardCategoryDto? = null,
            var createdAt: LocalDateTime? = null,
            var updatedAt: LocalDateTime? = null,
//            var comments: MutableList<CommentDto>? = mutableListOf(),
    )

    data class BoardFlatDto(
            var boardId: Long? = null,
            var title: String? = null,
            var description: String? = null,
            var userId: Long? = null,
            var username: String? = null,
            var email: String? = null,
            var categoryId: Long? = null,
            var categoryName: String? = null,
            var createdAt: LocalDateTime? = null,
            var updatedAt: LocalDateTime? = null,
//            var comments: MutableList<CommentFlatDto>? = mutableListOf()
    ) : Serializable {
        companion object {
            @JvmStatic
            private val serialVersionUID: Long = 1
        }
    }

    data class BoardCreateRequest(
            @field:NotBlank
            @field:Length(max = 200)
            var title: String,
            @field:NotBlank
            var description: String,
            @field:NotNull
            var categoryId: Long
    )

    data class BoardUpdateRequest(
            @field:NotBlank
            @field:Length(max = 200)
            var title: String,
            @field:NotBlank
            var description: String,
            @field:NotNull
            var categoryId: Long
    )

    data class BoardPagingCondition(
            var categoryName: String? = null,
            var keyword: String? = null
    )

    data class BoardPaging @QueryProjection constructor(
            var boardId: Long? = null,
            var title: String? = null,
            var description: String? = null,
            var userId: Long? = null,
            var username: String? = null,
            var email: String? = null,
            var boardCategoryId: Long? = null,
            var boardCategoryName: String? = null
    )
}