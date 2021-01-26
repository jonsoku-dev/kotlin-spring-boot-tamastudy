package com.tamastudy.tama.adapter.board

import com.tamastudy.tama.dto.board.BoardDto
import com.tamastudy.tama.dto.board.CreateBoardRequest
import com.tamastudy.tama.entity.Board
import com.tamastudy.tama.entity.BoardCategory
import com.tamastudy.tama.entity.User
import com.tamastudy.tama.entity.convertBoardCategory
import com.tamastudy.tama.mapper.BoardCategoryMapper
import com.tamastudy.tama.mapper.BoardMapper
import com.tamastudy.tama.service.board.BoardCategoryService
import com.tamastudy.tama.service.board.BoardService
import com.tamastudy.tama.service.user.UserService
import org.springframework.stereotype.Component

@Component
class BoardAdapterImpl(
        private val boardMapper: BoardMapper,
        private val boardCategoryMapper: BoardCategoryMapper,
        private val userService: UserService,
        private val boardCategoryService: BoardCategoryService,
        private val boardService: BoardService
) : BoardAdapter {
    override fun createBoard(user: User, createBoardRequest: CreateBoardRequest): BoardDto {
        val boardCategoryDto = boardCategoryService.findById(createBoardRequest.categoryId)
        val category = boardCategoryMapper.toEntity(boardCategoryDto)
//        val newBoard = Board().apply {
//            this.user = user
//            this.title = createBoardRequest.title
//            this.description = createBoardRequest.description
//            this.category = category
//        }
        val newBoard = boardMapper.createRequestToEntity(createBoardRequest).apply {
            this.user = user
            this.category = category
        }

        val dto = boardService.createBoard(newBoard)

        println(boardMapper.toEntity(dto))

        return dto
    }
}