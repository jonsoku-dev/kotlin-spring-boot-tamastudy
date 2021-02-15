package com.tamastudy.tama.service

import com.tamastudy.tama.dto.Board.*
import com.tamastudy.tama.dto.BoardCategory.BoardCategoryDto
import com.tamastudy.tama.dto.User.UserDto
import com.tamastudy.tama.entity.Board
import com.tamastudy.tama.entity.BoardCategory
import com.tamastudy.tama.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface BoardService {
    fun findAllWithComplexPage(condition: BoardPagingCondition, pageable: Pageable): Page<BoardPaging>
    fun findDtosWithComplexPage(condition: BoardPagingCondition, pageable: Pageable): Page<BoardFlatDto>
    fun retrieveById(id: Long): BoardDto
    fun findById(id: Long): BoardFlatDto
    fun createBoard(userDto: UserDto, categoryDto: BoardCategoryDto, createBoardCreateRequest: BoardCreateRequest): BoardFlatDto
    fun updateBoard(boardId: Long, userDto: UserDto, categoryDto: BoardCategoryDto, boardUpdateRequest: BoardUpdateRequest): BoardFlatDto
    fun deleteById(boardId: Long, userDto: UserDto)
}