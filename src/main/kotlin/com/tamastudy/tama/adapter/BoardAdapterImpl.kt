package com.tamastudy.tama.adapter

import com.tamastudy.tama.dto.Board.*
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
    override fun createBoard(user: User, boardCreateRequest: BoardCreateRequest): BoardDto {
        val foundCategory = BoardCategoryMapper.MAPPER.toEntity(boardCategoryService.findById(boardCreateRequest.categoryId))

        val newBoard = Board().apply {
            this.title = boardCreateRequest.title
            this.description = boardCreateRequest.description
            this.category = foundCategory
            this.user = user
        }

        return boardService.createBoard(newBoard)
    }

    override fun updateBoard(boardId: Long, user: User, boardUpdateRequest: BoardUpdateRequest): BoardDto {
        val foundCategory = BoardCategoryMapper.MAPPER.toEntity(boardCategoryService.findById(boardUpdateRequest.categoryId))
        return boardService.updateBoard(boardId, user, foundCategory, boardUpdateRequest)
    }
}