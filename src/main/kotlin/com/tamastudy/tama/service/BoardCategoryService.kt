package com.tamastudy.tama.service

import com.tamastudy.tama.dto.BoardCategoryDto.BoardCategoryInfo
import com.tamastudy.tama.entity.BoardCategory

interface BoardCategoryService {
    fun createCategory(category: BoardCategory): BoardCategoryInfo
    fun updateCategory(id: Long, category: BoardCategory): BoardCategoryInfo
    fun findById(id: Long): BoardCategoryInfo
    fun findAll(): List<BoardCategoryInfo>
    fun deleteById(id: Long)
}