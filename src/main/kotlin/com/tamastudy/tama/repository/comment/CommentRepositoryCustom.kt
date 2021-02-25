package com.tamastudy.tama.repository.comment

import com.tamastudy.tama.dto.CommentFlatDto
import com.tamastudy.tama.dto.CommentResponseDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CommentRepositoryCustom {
    fun searchPageDto(boardId: Long, pageable: Pageable): Page<CommentFlatDto>
    fun findAllFlatDto(boardId: Long): List<CommentFlatDto>
    fun findAllDto(boardId: Long) : List<CommentResponseDto>
    fun testing(boardId: Long)
}