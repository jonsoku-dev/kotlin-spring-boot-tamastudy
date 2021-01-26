package com.tamastudy.tama.adapter

import com.tamastudy.tama.dto.BoardDto.BoardCreateRequest
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
    override fun createBoard(user: User, createBoardRequest: BoardCreateRequest) =
            boardCategoryService.findById(createBoardRequest.categoryId)
                    .also { println("boardCategoryDto: $it") }
                    .let { boardCategoryDto ->
                        boardCategoryMapper.toEntity(boardCategoryDto)
                                .also { println("boardCategory: $it") }
                                .let { boardCategory ->
                                    boardMapper.createRequestToEntity(createBoardRequest)
                                            .also { println("board: $it") }
                                            .apply {
                                                this.user = user
                                                this.category = boardCategory
                                            }
                                            .also { println("newBoard: $it") }
                                            .let { newBoard ->
                                                boardService.createBoard(newBoard)
                                            }
                                }
                    }
}