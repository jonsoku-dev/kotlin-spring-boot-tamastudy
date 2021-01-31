package com.tamastudy.tama.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.querydsl.core.annotations.QueryProjection
import org.hibernate.validator.constraints.Length
import java.time.Instant
import java.time.LocalDateTime
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class Board {
    data class BoardDto(
            var id: Long? = null,
            var title: String? = null,
            var description: String? = null,
            var user: User.UserDto? = null,
            var category: BoardCategory.BoardCategoryDto? = null,
            var createdAt: LocalDateTime? = null,
            var updatedAt: LocalDateTime? = null,
    )

    data class BoardInfo(
            var boardId: Long? = null,
            var title: String? = null,
            var description: String? = null,
            var categoryId: Long? = null,
            var categoryName: String? = null,
            var userId: Long? = null,
            var username: String? = null,
            var email: String? = null,
    )

    data class BoardCreateRequest(
            @field:NotBlank
            @field:Length(max = 200)
            var title: String,
            @field:NotBlank
            @field:Length(max = 2000)
            var description: String,
            @field:NotNull
            var categoryId: Long
    )

    data class BoardUpdateRequest(
            @field:NotBlank
            @field:Length(max = 200)
            var title: String,
            @field:NotBlank
            @field:Length(max = 2000)
            var description: String,
            @field:NotNull
            var categoryId: Long
    )

    data class BoardPagingCondition(
            var title: String? = null,
            var username: String? = null,
    )

    data class BoardPaging @QueryProjection constructor(
            @JsonProperty("board_id")
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