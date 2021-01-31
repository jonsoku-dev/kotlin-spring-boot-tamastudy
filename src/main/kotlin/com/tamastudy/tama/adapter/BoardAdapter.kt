package com.tamastudy.tama.adapter

import com.tamastudy.tama.dto.Board.*
import com.tamastudy.tama.dto.User.UserDto
import com.tamastudy.tama.entity.User

interface BoardAdapter {
    fun createBoard(userDto: UserDto, boardCreateRequest: BoardCreateRequest): BoardDto
    fun updateBoard(boardId: Long, userDto: UserDto, boardUpdateRequest: BoardUpdateRequest): BoardDto
}