package com.tamastudy.tama.dto

import com.querydsl.core.annotations.QueryProjection
import org.hibernate.validator.constraints.Length
import java.io.Serializable
import java.time.LocalDateTime
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class BoardDto(
    var id: Long,
    var title: String,
    var description: String,
    var user: UserDto,
    var category: BoardCategoryDto,
    var createdAt: LocalDateTime,
    var updatedAt: LocalDateTime,
)

data class BoardFlatDto(
    var boardId: Long,
    var title: String,
    var description: String,
    var userId: Long,
    var username: String,
    var email: String,
    var categoryId: Long,
    var categoryName: String,
    var createdAt: LocalDateTime,
    var updatedAt: LocalDateTime,
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

data class BoardIds @QueryProjection constructor(
    var boardId: Long
)

data class BoardPaging @QueryProjection constructor(
    var boardId: Long,
    var title: String,
    var description: String,
    var userId: Long,
    var username: String,
    var email: String,
    var boardCategoryId: Long,
    var boardCategoryName: String
)
