package com.tamastudy.tama.repository

import com.tamastudy.tama.dto.BoardDto
import com.tamastudy.tama.dto.BoardDto.BoardPaging
import com.tamastudy.tama.dto.BoardDto.BoardPagingCondition
import com.tamastudy.tama.entity.Board
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface BoardRepositoryCustom {
    fun searchPageSimple(condition: BoardPagingCondition, pageable: Pageable): Page<BoardPaging>
    fun searchPageComplex(condition: BoardPagingCondition, pageable: Pageable): Page<BoardPaging>

    fun findOneWithUserById(boardId: Long): Board?
}
