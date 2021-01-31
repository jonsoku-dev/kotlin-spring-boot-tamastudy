package com.tamastudy.tama.adapter

import com.tamastudy.tama.dto.Board
import com.tamastudy.tama.dto.Board.BoardDto
import com.tamastudy.tama.entity.User

interface BoardAdapter {
    fun createBoard(user: User, boardCreateRequest: Board.BoardCreateRequest): BoardDto
    fun updateBoard(boardId: Long, user: User, boardUpdateRequest: Board.BoardUpdateRequest): BoardDto
}