package com.tamastudy.tama.service

import com.tamastudy.tama.dto.Board.*
import com.tamastudy.tama.entity.Board
import com.tamastudy.tama.entity.BoardCategory
import com.tamastudy.tama.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface BoardService {
    fun findAllWithComplexPage(condition: BoardPagingCondition, pageable: Pageable): Page<BoardPaging>
    fun findDtosWithComplexPage(condition: BoardPagingCondition, pageable: Pageable): Page<BoardDto>
    fun findById(id: Long): BoardDto
    fun createBoard(board: Board): BoardDto
    fun updateBoard(boardId: Long, user: User, category: BoardCategory ,boardUpdateRequest: BoardUpdateRequest): BoardDto
    fun deleteById(user: User, boardId: Long)
}