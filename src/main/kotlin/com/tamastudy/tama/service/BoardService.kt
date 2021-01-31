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
    fun findDtosWithComplexPage(condition: BoardPagingCondition, pageable: Pageable): Page<BoardDto>
    fun findById(id: Long): BoardDto
    fun createBoard(userDto: UserDto, categoryDto: BoardCategoryDto, createBoardCreateRequest: BoardCreateRequest): BoardDto
    fun updateBoard(boardId: Long, userDto: UserDto, categoryDto: BoardCategoryDto, boardUpdateRequest: BoardUpdateRequest): BoardDto
    fun deleteById(boardId: Long, userDto: UserDto)
}