package com.tamastudy.tama.adapter

import com.tamastudy.tama.dto.BoardDto.*
import com.tamastudy.tama.entity.User

interface BoardAdapter {
    fun createBoard(user: User, boardCreateRequest: BoardCreateRequest): BoardInfo
    fun updateBoard(boardId: Long, user: User, boardUpdateRequest: BoardUpdateRequest): BoardInfo
    fun deleteBoard(boardId: Long, user: User)
}