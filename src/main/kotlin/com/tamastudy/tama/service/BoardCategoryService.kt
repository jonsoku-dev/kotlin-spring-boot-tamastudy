package com.tamastudy.tama.service

import com.tamastudy.tama.dto.BoardCategory.*

interface BoardCategoryService {
    fun createCategory(boardCategoryCreateRequest: BoardCategoryCreateRequest): BoardCategoryDto
    fun updateCategory(id: Long, boardCategoryUpdateRequest: BoardCategoryUpdateRequest): BoardCategoryDto
    fun findById(id: Long): BoardCategoryDto
    fun findAll(): List<BoardCategoryDto>
    fun deleteById(id: Long)
}