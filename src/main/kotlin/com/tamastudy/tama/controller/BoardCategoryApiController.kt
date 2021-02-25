package com.tamastudy.tama.controller

import com.tamastudy.tama.dto.BoardCategoryCreateRequest
import com.tamastudy.tama.dto.BoardCategoryDto
import com.tamastudy.tama.dto.BoardCategoryUpdateRequest
import com.tamastudy.tama.service.category.BoardCategoryService
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
        val boardCategoryDtos = boardCategoryService.findAll()
        return ResponseEntity.status(HttpStatus.OK).body(boardCategoryDtos)
    }

    @PostMapping
    fun createCategory(
            @Valid @RequestBody boardCategoryCreateRequest: BoardCategoryCreateRequest
    ): ResponseEntity<BoardCategoryDto> {
        val boardCategoryDto = boardCategoryService.createCategory(boardCategoryCreateRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body(boardCategoryDto)
    }

    @PatchMapping("/{categoryId}")
    fun updateCategory(
            @PathVariable categoryId: Long,
            @Valid @RequestBody boardCategoryUpdateRequest: BoardCategoryUpdateRequest
    ): ResponseEntity<BoardCategoryDto> {
        val boardCategoryDto = boardCategoryService.updateCategory(categoryId, boardCategoryUpdateRequest)
        return ResponseEntity.status(HttpStatus.OK).body(boardCategoryDto)
    }

    @GetMapping("/{categoryId}")
    fun getCategory(
            @PathVariable categoryId: Long
    ): ResponseEntity<BoardCategoryDto> {
        val boardDto = boardCategoryService.findById(categoryId)
        return ResponseEntity.status(HttpStatus.OK).body(boardDto)
    }

    @DeleteMapping("/{categoryId}")
    fun deleteCategory(
            @PathVariable categoryId: Long
    ): ResponseEntity<Unit> {
        boardCategoryService.deleteById(categoryId)
        return ResponseEntity.noContent().build()
    }
}