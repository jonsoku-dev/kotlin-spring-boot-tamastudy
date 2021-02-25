package com.tamastudy.tama.service.board

import com.tamastudy.tama.dto.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice

interface BoardService {
    fun findIds(): MutableList<BoardIds>
    fun findAllWithComplexPage(condition: BoardPagingCondition, pageable: Pageable): Page<BoardPaging>
    fun findDtosWithPage(condition: BoardPagingCondition, pageable: Pageable): Page<BoardFlatDto>
    fun findDtosWithSlice(condition: BoardPagingCondition, pageable: Pageable): Slice<BoardFlatDto>
    fun retrieveById(id: Long): BoardDto
    fun findById(id: Long): BoardFlatDto
    fun createBoard(userDto: UserDto, categoryDto: BoardCategoryDto, createBoardCreateRequest: BoardCreateRequest): BoardFlatDto
    fun updateBoard(boardId: Long, userDto: UserDto, categoryDto: BoardCategoryDto, boardUpdateRequest: BoardUpdateRequest): BoardFlatDto
    fun deleteById(boardId: Long, userDto: UserDto)
}