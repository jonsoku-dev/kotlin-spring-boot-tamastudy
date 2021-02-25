package com.tamastudy.tama.service.category

import com.tamastudy.tama.dto.BoardCategoryCreateRequest
import com.tamastudy.tama.dto.BoardCategoryDto
import com.tamastudy.tama.dto.BoardCategoryUpdateRequest


interface BoardCategoryService {
    fun createCategory(boardCategoryCreateRequest: BoardCategoryCreateRequest): BoardCategoryDto
    fun updateCategory(id: Long, boardCategoryUpdateRequest: BoardCategoryUpdateRequest): BoardCategoryDto
    fun findById(id: Long): BoardCategoryDto
    fun findAll(): List<BoardCategoryDto>
    fun deleteById(id: Long)
}