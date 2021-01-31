package com.tamastudy.tama.controller

import com.tamastudy.tama.adapter.BoardAdapter
import com.tamastudy.tama.config.auth.PrincipalDetails
import com.tamastudy.tama.dto.Board.*
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

    @GetMapping("/v2")
    fun getBoardsV2(boardPagingCondition: BoardPagingCondition, pageable: Pageable): Page<BoardDto> {
        return boardService.findDtosWithComplexPage(boardPagingCondition, pageable)
    }

    @PostMapping
    fun createBoard(
            @Valid @RequestBody request: BoardCreateRequest
    ): ResponseEntity<BoardDto> {
        return ResponseEntity(
                boardAdapter.createBoard(
                        (SecurityContextHolder.getContext().authentication.principal as PrincipalDetails).getUserDto(),
                        request
                ), HttpStatus.CREATED
        )
    }

    @GetMapping("/{boardId}")
    fun getBoard(@PathVariable boardId: Long): ResponseEntity<BoardDto> {
        return ResponseEntity(boardService.findById(boardId), HttpStatus.OK)
    }

    @PatchMapping("/{boardId}")
    fun updateBoard(
            @PathVariable boardId: Long,
            @Valid @RequestBody request: BoardUpdateRequest
    ): ResponseEntity<BoardDto> {
        return ResponseEntity(
                boardAdapter.updateBoard(
                        boardId,
                        (SecurityContextHolder.getContext().authentication.principal as PrincipalDetails).getUserDto(),
                        request
                ),
                HttpStatus.CREATED
        )
    }

    @DeleteMapping("/{boardId}")
    fun deleteBoard(
            @PathVariable boardId: Long
    ): ResponseEntity<Unit> {
        return boardService.deleteById(
                boardId,
                (SecurityContextHolder.getContext().authentication.principal as PrincipalDetails).getUserDto()
        ).let {
            ResponseEntity.noContent().build()
        }
    }
}