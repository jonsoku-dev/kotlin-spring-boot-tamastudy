package com.tamastudy.tama.dto

import org.hibernate.validator.constraints.Length
import java.io.Serializable
import java.time.LocalDateTime
import javax.validation.constraints.NotBlank

data class CommentDto(
    var id: Long,
    var text: String,
    var user: UserDto? = null,
    var board: BoardDto? = null,
    var createdAt: LocalDateTime,
    var updatedAt: LocalDateTime,
    var superComment: CommentDto?,
    var subComment: MutableList<CommentDto> = mutableListOf<CommentDto>(),
    var level: Int? = null,
    var isLive: Boolean? = null,
) : Serializable {
    companion object {
        @JvmStatic
        private val serialVersionUID: Long = 1
    }
}

data class CommentFlatDto(
    var commentId: Long,
    var text: String,
    var userId: Long,
    var username: String,
    var createdAt: LocalDateTime,
    var updatedAt: LocalDateTime,
    var superComment: CommentFlatDto? = null,
    var subComment: MutableList<CommentDto> = mutableListOf<CommentDto>(),
    var level: Int,
    var isLive: Boolean,
) : Serializable {
    companion object {
        @JvmStatic
        private val serialVersionUID: Long = 1
    }
}

data class CommentResponseDto(
    var commentId: Long,
    var text: String,
    var userId: Long,
    var username: String,
    var createdAt: LocalDateTime,
    var updatedAt: LocalDateTime,
    var level: Int,
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
