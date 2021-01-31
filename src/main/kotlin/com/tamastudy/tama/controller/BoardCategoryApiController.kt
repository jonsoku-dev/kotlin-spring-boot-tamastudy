package com.tamastudy.tama.controller

import com.tamastudy.tama.dto.BoardCategory.*
import com.tamastudy.tama.entity.Board
import com.tamastudy.tama.entity.BoardCategory
import com.tamastudy.tama.mapper.BoardCategoryMapper
import com.tamastudy.tama.service.BoardCategoryService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/category")
class BoardCategoryApiController(
        private val boardCategoryService: BoardCategoryService
) {
    @GetMapping
    fun getCategories(): ResponseEntity<List<BoardCategoryDto>> {
        return ResponseEntity(boardCategoryService.findAll(), HttpStatus.OK)
    }

    @PostMapping
    fun createCategory(
            @Valid @RequestBody request: BoardCategoryCreateRequest
    ): ResponseEntity<BoardCategoryDto> {
        val category = BoardCategory().apply {
            this.name = request.name
        }
        return ResponseEntity(boardCategoryService.createCategory(category), HttpStatus.CREATED)
    }

    @PatchMapping("/{categoryId}")
    fun updateCategory(
            @PathVariable categoryId: Long,
            @Valid @RequestBody request: BoardCategoryUpdateRequest
    ): ResponseEntity<BoardCategoryDto> {
        return ResponseEntity(boardCategoryService.updateCategory(categoryId, request), HttpStatus.OK)
    }

    @GetMapping("/{categoryId}")
    fun getCategory(
            @PathVariable categoryId: Long
    ): ResponseEntity<BoardCategoryDto> {
        return ResponseEntity(boardCategoryService.findById(categoryId), HttpStatus.OK)
    }

    @DeleteMapping("/{categoryId}")
    fun deleteCategory(
            @PathVariable categoryId: Long
    ): ResponseEntity<Unit> {
        boardCategoryService.deleteById(categoryId)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}