package com.tamastudy.tama.adapter.board

import com.tamastudy.tama.dto.board.BoardDto
import com.tamastudy.tama.dto.board.CreateBoardRequest
import com.tamastudy.tama.entity.User

interface BoardAdapter {
    fun createBoard(user: User, createBoardRequest: CreateBoardRequest): BoardDto
}