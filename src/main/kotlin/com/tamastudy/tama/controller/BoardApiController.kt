package com.tamastudy.tama.controller

import com.tamastudy.tama.adapter.BoardAdapter
import com.tamastudy.tama.security.auth.PrincipalDetails
import com.tamastudy.tama.dto.Board.*
import com.tamastudy.tama.security.jwt.TokenProvider
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
        private val tokenProvider: TokenProvider,
        private val boardAdapter: BoardAdapter,
        private val boardService: BoardService,
) {
    @GetMapping
    fun getBoards(boardPagingCondition: BoardPagingCondition, pageable: Pageable): Page<BoardPaging> {
        return boardService.findAllWithComplexPage(boardPagingCondition, pageable)
    }

    @GetMapping("/v2")
    fun getBoardsV2(boardPagingCondition: BoardPagingCondition, pageable: Pageable): Page<BoardDto> {
        return boardService.findDtosWithComplexPage(boardPagingCondition, pageable)
    }

    @PostMapping
    fun createBoard(
            @Valid @RequestBody boardCreateRequest: BoardCreateRequest
    ): ResponseEntity<BoardDto> {
        val userDto = (SecurityContextHolder.getContext().authentication.principal as PrincipalDetails).getUserDto()
        val boardDto = boardAdapter.createBoard(userDto, boardCreateRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body(boardDto)
    }

    @GetMapping("/{boardId}")
    fun getBoard(@PathVariable boardId: Long): ResponseEntity<BoardDto> {
        val boardDto = boardService.findById(boardId)
        return ResponseEntity.status(HttpStatus.OK).body(boardDto)
    }

    @PatchMapping("/{boardId}")
    fun updateBoard(
            @PathVariable boardId: Long,
            @Valid @RequestBody boardUpdateRequest: BoardUpdateRequest
    ): ResponseEntity<BoardDto> {
        val userDto = (SecurityContextHolder.getContext().authentication.principal as PrincipalDetails).getUserDto()
        val boardDto = boardAdapter.updateBoard(boardId, userDto, boardUpdateRequest)
        return ResponseEntity.status(HttpStatus.OK).body(boardDto)
    }

    @DeleteMapping("/{boardId}")
    fun deleteBoard(
            @PathVariable boardId: Long
    ): ResponseEntity<Unit> {
        val userDto = (SecurityContextHolder.getContext().authentication.principal as PrincipalDetails).getUserDto()
        boardService.deleteById(boardId, userDto)
        return ResponseEntity.noContent().build()
    }
}