package com.tamastudy.tama.adapter

import com.tamastudy.tama.dto.BoardDto.*
import com.tamastudy.tama.entity.User

interface BoardAdapter {
    fun createBoard(user: User, createBoardRequest: BoardCreateRequest): BoardInfo
}