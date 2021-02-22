package com.tamastudy.tama.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.tamastudy.tama.dto.Board.BoardDto
import com.tamastudy.tama.dto.User.UserDto
import com.tamastudy.tama.entity.Comment
import org.hibernate.validator.constraints.Length
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class Comment {
    data class CommentDto(
            var id: Long? = null,
            var text: String? = null,
            var user: UserDto? = null,
            var board: BoardDto? = null,
            var createdAt: LocalDateTime? = null,
            var updatedAt: LocalDateTime? = null,
            var superComment: CommentDto? = null,
            var subComment: MutableList<CommentDto>? = mutableListOf<CommentDto>(),
            var level: Int? = null,
            var isLive: Boolean? = true,
    ) : Serializable {
        companion object {
            @JvmStatic
            private val serialVersionUID: Long = 1
        }
    }

    data class CommentFlatDto(
            var commentId: Long? = null,
            var text: String? = null,
            var userId: Long? = null,
            var username: String? = null,
            var createdAt: LocalDateTime? = null,
            var updatedAt: LocalDateTime? = null,
            var superComment: CommentDto? = null,
            var subComment: MutableList<CommentDto>? = mutableListOf<CommentDto>(),
            var level: Int? = null,
            var isLive: Boolean? = true,
    ) : Serializable {
        companion object {
            @JvmStatic
            private val serialVersionUID: Long = 1
        }
    }

    data class CommentCreateRequest(
            @field:NotBlank
            @field:Length(max = 255)
            var text: String,
            var commentId: Long? = null,
    )

    data class CommentUpdateRequest(
            @field:Length(max = 255)
            var text: String,
    )
}