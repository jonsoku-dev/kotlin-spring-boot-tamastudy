package com.tamastudy.tama.adapter

import com.tamastudy.tama.dto.BoardDto.*
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
    override fun createBoard(user: User, boardCreateRequest: BoardCreateRequest): BoardInfo {
        val category = BoardCategoryMapper.MAPPER.toEntity(boardCategoryService.findById(boardCreateRequest.categoryId))
        val newBoard = BoardMapper.MAPPER.createRequestToEntity(boardCreateRequest).apply {
            this.category = category
            this.user = user
        }
        return boardService.createBoard(newBoard)
    }

    override fun updateBoard(boardId: Long, user: User, boardUpdateRequest: BoardUpdateRequest): BoardInfo {
        val boardInfo = checkPermissionAndReturnBoardInfo(boardId, user)
        val category = BoardCategoryMapper.MAPPER.toEntity(boardCategoryService.findById(boardUpdateRequest.categoryId))
        val updateBoard = BoardMapper.MAPPER.toEntity(boardInfo).apply {
            this.title = boardUpdateRequest.title
            this.description = boardUpdateRequest.description
            this.category = category
        }
        return boardService.updateBoard(updateBoard)
    }

    override fun deleteBoard(boardId: Long, user: User) {
        checkPermissionAndReturnBoardInfo(boardId, user)
        boardService.deleteById(boardId)
    }

    private fun checkPermissionAndReturnBoardInfo(boardId: Long, user: User): BoardInfo {
        boardService.findById(boardId).let { boardInfo ->
            if (boardInfo.userId != user.id) {
                throw IllegalAccessException("권한이 없습니다.")
            }
            return boardInfo
        }
    }
}