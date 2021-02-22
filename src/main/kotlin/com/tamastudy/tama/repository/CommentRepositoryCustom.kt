package com.tamastudy.tama.repository

import com.tamastudy.tama.dto.Comment
import com.tamastudy.tama.dto.Comment.CommentDto
import com.tamastudy.tama.dto.Comment.CommentFlatDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CommentRepositoryCustom {
    fun searchPageDto(boardId: Long, pageable: Pageable): Page<CommentFlatDto>
    fun findAllFlatDto(boardId: Long): List<CommentFlatDto>
    fun findAllDto(boardId: Long) : List<CommentDto>
    fun testing(boardId: Long)
}