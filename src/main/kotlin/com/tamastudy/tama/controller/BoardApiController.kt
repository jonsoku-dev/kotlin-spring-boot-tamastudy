package com.tamastudy.tama.controller

import com.tamastudy.tama.dto.*
import com.tamastudy.tama.service.board.BoardService
import com.tamastudy.tama.service.category.BoardCategoryService
import com.tamastudy.tama.util.PrincipalDetails
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class BoardApiController(
        private val boardService: BoardService,
        private val boardCategoryService: BoardCategoryService,
) {


    @GetMapping("/v1/board/ids")
    fun getBoardIds(): MutableList<BoardIds> {
        return boardService.findIds()
    }

    @GetMapping("/v1/board")
    fun getBoardsV1(boardPagingCondition: BoardPagingCondition,
                    @PageableDefault(
                            size = 12,
                            sort = ["createdAt"],
                            direction = Sort.Direction.DESC
                    )
                    pageable: Pageable): Page<BoardFlatDto> {
        println("get boards!")
        return boardService.findDtosWithPage(boardPagingCondition, pageable)
    }

    @GetMapping("/v1/bo")
    fun getBo(): ResponseEntity<Unit> {
        return ResponseEntity.ok().body(null)
    }

    @GetMapping("/v2/board")
    fun getBoards(boardPagingCondition: BoardPagingCondition,
                    @PageableDefault(
                            size = 12,
                            sort = ["createdAt"],
                            direction = Sort.Direction.DESC
                    )
                    pageable: Pageable): Page<BoardFlatDto> {
        return boardService.findDtosWithPage(boardPagingCondition, pageable)
    }

    @PostMapping("/v1/board")
    fun createBoard(
            @Valid @RequestBody boardCreateRequest: BoardCreateRequest
    ): ResponseEntity<BoardFlatDto> {
        val userDto = (SecurityContextHolder.getContext().authentication.principal as PrincipalDetails).getUserDto()
        val categoryDto = boardCategoryService.findById(boardCreateRequest.categoryId)
        val boardDto = boardService.createBoard(userDto, categoryDto, boardCreateRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body(boardDto)
    }

    @GetMapping("/v1/board/{boardId}")
    fun getBoard(@PathVariable boardId: Long): ResponseEntity<BoardFlatDto> {
        val boardDto = boardService.findById(boardId)
        return ResponseEntity.status(HttpStatus.OK).body(boardDto)
    }

    @PatchMapping("/v1/board/{boardId}")
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

    @DeleteMapping("/v1/board/{boardId}")
    fun deleteBoard(
            @PathVariable boardId: Long
    ): ResponseEntity<Unit> {
        val userDto = (SecurityContextHolder.getContext().authentication.principal as PrincipalDetails).getUserDto()
        boardService.deleteById(boardId, userDto)
        return ResponseEntity.noContent().build()
    }
}