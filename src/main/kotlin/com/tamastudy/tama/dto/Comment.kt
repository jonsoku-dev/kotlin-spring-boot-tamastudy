package com.tamastudy.tama.dto

import org.hibernate.validator.constraints.Length
import java.io.Serializable
import java.time.LocalDateTime
import javax.validation.constraints.NotBlank

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

data class CommentResponseDto(
        var commentId: Long? = null,
        var text: String? = null,
        var userId: Long? = null,
        var username: String? = null,
        var createdAt: LocalDateTime? = null,
        var updatedAt: LocalDateTime? = null,
        var level: Int? = null,
        var isLive: Boolean? = true,
        var subComment: MutableList<CommentResponseDto> = mutableListOf()
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
