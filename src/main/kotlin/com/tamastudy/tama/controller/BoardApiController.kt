package com.tamastudy.tama.controller

import com.tamastudy.tama.dto.Board.*
import com.tamastudy.tama.security.auth.PrincipalDetails
import com.tamastudy.tama.service.BoardCategoryService
import com.tamastudy.tama.service.BoardService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/board")
class BoardApiController(
        private val boardService: BoardService,
        private val boardCategoryService: BoardCategoryService,
) {
    @GetMapping
    fun getBoardsV2(boardPagingCondition: BoardPagingCondition,
                    @PageableDefault(
                            size = 12,
                            sort = ["createdAt"],
                            direction = Sort.Direction.DESC
                    )
                    pageable: Pageable): Page<BoardFlatDto> {
        println("boardPagingCondition.categoryName: ${boardPagingCondition.categoryName}")
        return boardService.findDtosWithComplexPage(boardPagingCondition, pageable)
    }

    @PostMapping
    fun createBoard(
            @Valid @RequestBody boardCreateRequest: BoardCreateRequest
    ): ResponseEntity<BoardFlatDto> {
        val userDto = (SecurityContextHolder.getContext().authentication.principal as PrincipalDetails).getUserDto()
        val categoryDto = boardCategoryService.findById(boardCreateRequest.categoryId)
        val boardDto =  boardService.createBoard(userDto, categoryDto, boardCreateRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body(boardDto)
    }

    @GetMapping("/{boardId}")
    fun getBoard(@PathVariable boardId: Long): ResponseEntity<BoardFlatDto> {
        val boardDto = boardService.findById(boardId)
        return ResponseEntity.status(HttpStatus.OK).body(boardDto)
    }

    @PatchMapping("/{boardId}")
    fun updateBoard(
            @PathVariable boardId: Long,
            @Valid @RequestBody boardUpdateRequest: BoardUpdateRequest
    ): ResponseEntity<BoardFlatDto> {
        val userDto = (SecurityContextHolder.getContext().authentication.principal as PrincipalDetails).getUserDto()
        val boardDto = boardService.updateBoard(
                boardId,
                userDto,
                boardCategoryService.findById(boardUpdateRequest.categoryId),
                boardUpdateRequest
        )
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