package com.tamastudy.tama.service

import com.tamastudy.tama.dto.BoardCategoryDto
import com.tamastudy.tama.entity.BoardCategory

interface BoardCategoryService {
    fun createCategory(category: BoardCategory): BoardCategoryDto.BoardCategoryInfo
    fun updateCategory(category: BoardCategory): BoardCategoryDto.BoardCategoryInfo
    fun findById(id: Long) : BoardCategoryDto.BoardCategoryInfo
}