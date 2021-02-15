package com.tamastudy.tama.repository

import com.tamastudy.tama.dto.Board.BoardPaging
import com.tamastudy.tama.dto.Board.BoardPagingCondition
import com.tamastudy.tama.dto.Board.BoardFlatDto
import com.tamastudy.tama.entity.Board
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface BoardRepositoryCustom {
    fun searchPageSimple(condition: BoardPagingCondition, pageable: Pageable): Page<BoardPaging>
    fun searchPageComplex(condition: BoardPagingCondition, pageable: Pageable): Page<BoardPaging>
    fun searchPageDto(condition: BoardPagingCondition, pageable: Pageable): Page<BoardFlatDto>

    fun findOneWithUserById(boardId: Long): Board?
}
