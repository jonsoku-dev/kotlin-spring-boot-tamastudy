package com.tamastudy.tama.controller

import com.tamastudy.tama.adapter.BoardAdapter
import com.tamastudy.tama.config.auth.PrincipalDetails
import com.tamastudy.tama.dto.BoardDto.*
import com.tamastudy.tama.service.BoardService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/board")
class BoardApiController(
        private val boardAdapter: BoardAdapter,
        private val boardService: BoardService,
) {
    @GetMapping
    fun getBoards(boardPagingCondition: BoardPagingCondition, pageable: Pageable): Page<BoardPaging> {
        return boardService.findAllWithComplexPage(boardPagingCondition, pageable)
    }

    @PostMapping
    fun createBoard(
            @Valid @RequestBody request: BoardCreateRequest
    ): ResponseEntity<BoardInfo> {
        val user = (SecurityContextHolder.getContext().authentication.principal as PrincipalDetails).getUserEntity()
        return ResponseEntity(boardAdapter.createBoard(user, request), HttpStatus.CREATED)
    }

    @GetMapping("/{boardId}")
    fun getBoard(@PathVariable boardId: Long): ResponseEntity<BoardInfo> {
        return ResponseEntity(boardService.findById(boardId), HttpStatus.OK)
    }

    @PatchMapping("/{boardId}")
    fun updateBoard(
            @PathVariable boardId: Long,
            @Valid @RequestBody request: BoardUpdateRequest
    ): ResponseEntity<BoardInfo> {
        val user = (SecurityContextHolder.getContext().authentication.principal as PrincipalDetails).getUserEntity()
        val updatedBoard = boardAdapter.updateBoard(boardId, user, request)
        return ResponseEntity(updatedBoard, HttpStatus.OK)
    }

    @DeleteMapping("/{boardId}")
    fun deleteBoard(
            @PathVariable boardId: Long
    ): ResponseEntity<Unit> {
        val user = (SecurityContextHolder.getContext().authentication.principal as PrincipalDetails).getUserEntity()
        boardAdapter.deleteBoard(boardId, user)
        return ResponseEntity.noContent().build()
    }
}