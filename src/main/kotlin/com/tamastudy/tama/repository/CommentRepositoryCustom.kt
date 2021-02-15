package com.tamastudy.tama.repository

import com.tamastudy.tama.dto.Comment
import com.tamastudy.tama.dto.Comment.CommentDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CommentRepositoryCustom {
    fun searchPageDto(boardId: Long, pageable: Pageable): Page<Comment.CommentFlatDto>
    fun findAllFlatDto(boardId: Long): List<Comment.CommentFlatDto>
}