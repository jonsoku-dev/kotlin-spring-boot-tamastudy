package com.tamastudy.tama.adapter

import com.tamastudy.tama.dto.BoardDto.BoardCreateRequest
import com.tamastudy.tama.dto.BoardDto.BoardInfo
import com.tamastudy.tama.entity.Board
import com.tamastudy.tama.entity.User
import com.tamastudy.tama.mapper.BoardCategoryMapper
import com.tamastudy.tama.mapper.BoardMapper
import com.tamastudy.tama.service.BoardCategoryService
import com.tamastudy.tama.service.BoardService
import com.tamastudy.tama.service.UserService
import org.springframework.stereotype.Component

@Component
class BoardAdapterImpl(
        private val boardMapper: BoardMapper,
        private val boardCategoryMapper: BoardCategoryMapper,
        private val userService: UserService,
        private val boardCategoryService: BoardCategoryService,
        private val boardService: BoardService
) : BoardAdapter {
    override fun createBoard(user: User, createBoardRequest: BoardCreateRequest): BoardInfo {
        val category = boardCategoryMapper.toEntity(boardCategoryService.findById(createBoardRequest.categoryId))
        val newBoard = Board().apply {
            this.title = createBoardRequest.title
            this.description = createBoardRequest.description
            this.category = category
            this.user = user
        }
        return boardService.createBoard(newBoard)
    }

}