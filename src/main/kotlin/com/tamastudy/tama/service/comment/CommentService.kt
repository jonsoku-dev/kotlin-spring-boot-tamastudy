package com.tamastudy.tama.service

import com.tamastudy.tama.dto.Board
import com.tamastudy.tama.dto.Comment.*
import com.tamastudy.tama.dto.User.UserDto
import com.tamastudy.tama.entity.Comment
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CommentService {
    fun update(boardId: Long, commentId: Long, userDto: UserDto, commentUpdateRequest: CommentUpdateRequest): CommentFlatDto
    fun delete(boardId: Long, commentId: Long, userDto: UserDto)
    fun save(boardDto: Board.BoardDto, userDto: UserDto, commentCreateRequest: CommentCreateRequest): CommentDto
    fun searchPageDto(boardId: Long, pageable: Pageable): Page<CommentFlatDto>
    fun findAllFlatDto(boardId: Long): List<CommentFlatDto>
    fun findAllDto(boardId: Long): List<CommentResponseDto>
}