package com.tamastudy.tama.controller

import com.tamastudy.tama.adapter.board.BoardAdapter
import com.tamastudy.tama.config.auth.PrincipalDetails
import com.tamastudy.tama.dto.board.*
import com.tamastudy.tama.entity.BoardCategory
import com.tamastudy.tama.service.board.BoardCategoryService
import com.tamastudy.tama.service.board.BoardService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
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
            @Valid @RequestBody request: CreateBoardCategoryRequest
    ): ResponseEntity<BoardCategoryDto> {
        val category = BoardCategory().apply {
            this.name = request.name
        }
        return ResponseEntity(boardCategoryService.createCategory(category), HttpStatus.CREATED)
    }

    @PostMapping("/board")
    fun createBoard(
            @Valid @RequestBody request: CreateBoardRequest
    ): ResponseEntity<BoardDto> {
        val user = (SecurityContextHolder.getContext().authentication.principal as PrincipalDetails).getUserEntity()
        return ResponseEntity(boardAdapter.createBoard(user, request), HttpStatus.CREATED)
    }
}