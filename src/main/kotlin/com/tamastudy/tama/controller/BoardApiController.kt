package com.tamastudy.tama.controller

import com.tamastudy.tama.adapter.BoardAdapter
import com.tamastudy.tama.config.auth.PrincipalDetails
import com.tamastudy.tama.dto.BoardCategoryDto.BoardCategoryCreateRequest
import com.tamastudy.tama.dto.BoardCategoryDto.BoardCategoryInfo
import com.tamastudy.tama.dto.BoardDto
import com.tamastudy.tama.entity.BoardCategory
import com.tamastudy.tama.service.BoardCategoryService
import com.tamastudy.tama.service.BoardService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1")
class BoardApiController(
        private val boardAdapter: BoardAdapter,
        private val boardService: BoardService,
        private val boardCategoryService: BoardCategoryService,
) {
    @PostMapping("/category")
    fun createCategory(
            @Valid @RequestBody request: BoardCategoryCreateRequest
    ): ResponseEntity<BoardCategoryInfo> {
        val category = BoardCategory().apply {
            this.name = request.name
        }
        return ResponseEntity(boardCategoryService.createCategory(category), HttpStatus.CREATED)
    }

    @PostMapping("/board")
    fun createBoard(
            @Valid @RequestBody request: BoardDto.BoardCreateRequest
    ): ResponseEntity<BoardDto.BoardInfo> {
        val user = (SecurityContextHolder.getContext().authentication.principal as PrincipalDetails).getUserEntity()
        return ResponseEntity(boardAdapter.createBoard(user, request), HttpStatus.CREATED)
    }
}