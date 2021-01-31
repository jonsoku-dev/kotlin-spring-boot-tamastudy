package com.tamastudy.tama.service

import com.tamastudy.tama.dto.BoardCategory.BoardCategoryDto
import com.tamastudy.tama.dto.BoardCategory.BoardCategoryUpdateRequest
import com.tamastudy.tama.entity.BoardCategory

interface BoardCategoryService {
    fun createCategory(category: BoardCategory): BoardCategoryDto
    fun updateCategory(id: Long, request: BoardCategoryUpdateRequest): BoardCategoryDto
    fun findById(id: Long): BoardCategoryDto
    fun findAll(): List<BoardCategoryDto>
    fun deleteById(id: Long)
}