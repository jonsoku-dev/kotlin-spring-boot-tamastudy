package com.tamastudy.tama.repository

import com.tamastudy.tama.dto.Board.*
import com.tamastudy.tama.entity.Board
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice

interface BoardRepositoryCustom {
    fun searchPageSimple(condition: BoardPagingCondition, pageable: Pageable): Page<BoardPaging>
    fun searchPageComplex(condition: BoardPagingCondition, pageable: Pageable): Page<BoardPaging>
    fun searchPageDto(condition: BoardPagingCondition, pageable: Pageable): Page<BoardFlatDto>
    fun searchSliceDto(condition: BoardPagingCondition, pageable: Pageable): Slice<BoardFlatDto>
    fun findIds(): MutableList<BoardIds>
    fun findOneWithUserById(boardId: Long): Board?
}
