package com.tamastudy.tama.controller

import com.tamastudy.tama.dto.BoardCategoryDto
import com.tamastudy.tama.entity.BoardCategory
import com.tamastudy.tama.service.BoardCategoryService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/category")
class BoardCategoryApiController(
        private val boardCategoryService: BoardCategoryService
) {
    @PostMapping
    fun createCategory(
            @Valid @RequestBody request: BoardCategoryDto.BoardCategoryCreateRequest
    ): ResponseEntity<BoardCategoryDto.BoardCategoryInfo> {
        val category = BoardCategory().apply {
            this.name = request.name
        }
        return ResponseEntity(boardCategoryService.createCategory(category), HttpStatus.CREATED)
    }
}