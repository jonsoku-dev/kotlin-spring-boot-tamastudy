package com.tamastudy.tama.service

import com.tamastudy.tama.dto.BoardDto.*
import com.tamastudy.tama.entity.Board
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface BoardService {
    fun findAllWithPage(condition: BoardPagingCondition?, pageable: Pageable?): Page<BoardPaging>
    fun findAllWithComplexPage(condition: BoardPagingCondition?, pageable: Pageable?): Page<BoardPaging>
    fun findById(id: Long): BoardInfo
    fun createBoard(board: Board): BoardInfo
    fun updateBoard(board: Board): BoardInfo
    fun deleteById(id: Long)
}