package com.tamastudy.tama.repository.board

import com.tamastudy.tama.dto.board.BoardDto
import com.tamastudy.tama.dto.board.BoardPagingCondition
import com.tamastudy.tama.dto.board.BoardPagingDto
import com.tamastudy.tama.entity.Board
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface BoardRepositoryCustom {
    fun searchPageSimple(condition: BoardPagingCondition?, pageable: Pageable?): Page<BoardPagingDto>
    fun searchPageComplex(condition: BoardPagingCondition?, pageable: Pageable?): Page<BoardPagingDto>

    fun findOneWithUserById(boardId: Long): Board?
}
