package com.tamastudy.tama.service.board

import com.tamastudy.tama.dto.board.*
import com.tamastudy.tama.entity.Board
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface BoardService {
    fun findAllWithPage(condition: BoardPagingCondition?, pageable: Pageable?): Page<BoardPagingDto>
    fun findAllWithComplexPage(condition: BoardPagingCondition?, pageable: Pageable?): Page<BoardPagingDto>
    fun findById(id: Long): BoardDto
    fun createBoard(board: Board): BoardDto
    fun updateBoard(board: Board): BoardDto
    fun deleteById(id: Long)
}