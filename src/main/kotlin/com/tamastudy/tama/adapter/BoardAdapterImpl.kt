package com.tamastudy.tama.adapter

import com.tamastudy.tama.dto.Board.*
import com.tamastudy.tama.dto.User.UserDto
import com.tamastudy.tama.entity.Board
import com.tamastudy.tama.entity.User
import com.tamastudy.tama.mapper.BoardCategoryMapper
import com.tamastudy.tama.mapper.BoardMapper
import com.tamastudy.tama.service.BoardCategoryService
import com.tamastudy.tama.service.BoardService
import org.springframework.stereotype.Component

@Component
class BoardAdapterImpl(
        private val boardCategoryService: BoardCategoryService,
        private val boardService: BoardService,
) : BoardAdapter {
    override fun createBoard(userDto: UserDto, boardCreateRequest: BoardCreateRequest): BoardDto {
        val categoryDto = boardCategoryService.findById(boardCreateRequest.categoryId)
        return boardService.createBoard(userDto, categoryDto, boardCreateRequest)
    }

    override fun updateBoard(boardId: Long, userDto: UserDto, boardUpdateRequest: BoardUpdateRequest): BoardDto {
        return boardService.updateBoard(
                boardId,
                userDto,
                boardCategoryService.findById(boardUpdateRequest.categoryId),
                boardUpdateRequest
        )
    }
}