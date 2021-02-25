package com.tamastudy.tama.service.comment

import com.tamastudy.tama.dto.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CommentService {
    fun update(boardId: Long, commentId: Long, userDto: UserDto, commentUpdateRequest: CommentUpdateRequest): CommentFlatDto
    fun delete(boardId: Long, commentId: Long, userDto: UserDto)
    fun save(boardDto: BoardDto, userDto: UserDto, commentCreateRequest: CommentCreateRequest): CommentDto
    fun searchPageDto(boardId: Long, pageable: Pageable): Page<CommentFlatDto>
    fun findAllFlatDto(boardId: Long): List<CommentFlatDto>
    fun findAllDto(boardId: Long): List<CommentResponseDto>
}