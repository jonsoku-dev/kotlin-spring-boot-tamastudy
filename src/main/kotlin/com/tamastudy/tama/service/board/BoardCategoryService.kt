package com.tamastudy.tama.service.board

import com.tamastudy.tama.dto.board.BoardCategoryDto
import com.tamastudy.tama.entity.BoardCategory

interface BoardCategoryService {
    fun createCategory(category: BoardCategory): BoardCategoryDto
    fun updateCategory(category: BoardCategory): BoardCategoryDto
    fun findById(id: Long) : BoardCategoryDto
}