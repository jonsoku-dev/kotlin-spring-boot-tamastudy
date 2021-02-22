package com.tamastudy.tama.controller

import com.tamastudy.tama.dto.Board.BoardDto
import com.tamastudy.tama.dto.Comment.*
import com.tamastudy.tama.service.CommentService
import com.tamastudy.tama.util.PrincipalDetails
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
class CommentApiController(
        private val commentService: CommentService,
) {
    @PostMapping("/{boardId}/comment")
    fun createComment(@PathVariable boardId: Long, @Valid @RequestBody commentCreateRequest: CommentCreateRequest): ResponseEntity<CommentDto> {
        val userDto = (SecurityContextHolder.getContext().authentication.principal as PrincipalDetails).getUserDto()
        val boardDto = BoardDto().apply {
            this.id = boardId
        }
        val newComment = commentService.save(boardDto, userDto, commentCreateRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body(newComment)
    }

    @PatchMapping("/{boardId}/comment/{commentId}")
    fun updateComment(@PathVariable boardId: Long, @PathVariable commentId: Long, @Valid @RequestBody commentUpdateRequest: CommentUpdateRequest): ResponseEntity<CommentFlatDto> {
        val userDto = (SecurityContextHolder.getContext().authentication.principal as PrincipalDetails).getUserDto()
        val updatedComment = commentService.update(boardId, commentId, userDto, commentUpdateRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedComment)
    }

    @DeleteMapping("/{boardId}/comment/{commentId}")
    fun deleteComment(@PathVariable boardId: Long, @PathVariable commentId: Long): ResponseEntity<Unit> {
        val userDto = (SecurityContextHolder.getContext().authentication.principal as PrincipalDetails).getUserDto()
        commentService.delete(boardId, commentId, userDto)
        return ResponseEntity.status(HttpStatus.OK).build()
    }

    @GetMapping("/{boardId}/comment/paging")
    fun searchPage(
            @PathVariable boardId: Long,
            @PageableDefault(
                    size = 6,
                    sort = ["createdAt"],
                    direction = Sort.Direction.DESC
            )
            pageable: Pageable
    ): Page<CommentFlatDto> {
        return commentService.searchPageDto(boardId, pageable)
    }

    @GetMapping("/{boardId}/comment")
    fun findAll(@PathVariable boardId: Long): ResponseEntity<List<CommentDto>> {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.findAllDto(boardId))
    }
}