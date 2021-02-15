package com.tamastudy.tama.service

import com.tamastudy.tama.dto.Board
import com.tamastudy.tama.dto.Comment
import com.tamastudy.tama.dto.Comment.CommentDto
import com.tamastudy.tama.dto.Comment.CommentFlatDto
import com.tamastudy.tama.dto.User
import com.tamastudy.tama.mapper.BoardMapper
import com.tamastudy.tama.mapper.CommentMapper
import com.tamastudy.tama.mapper.UserMapper
import com.tamastudy.tama.repository.CommentRepository
import javassist.NotFoundException
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CommentServiceImpl(
        private val commentRepository: CommentRepository,
        private val userMapper: UserMapper,
        private val boardMapper: BoardMapper,
        private val commentMapper: CommentMapper
) : CommentService {
    @Caching(evict = [
        CacheEvict(value = ["board"], key = "#boardDto.id"),
        CacheEvict(value = ["comments"], allEntries = true)
    ])
    override fun save(boardDto: Board.BoardDto, userDto: User.UserDto, commentCreateRequest: Comment.CommentCreateRequest): CommentFlatDto {
        val userEntity = userMapper.toEntity(userDto)
        val boardEntity = boardMapper.toEntity(boardDto)
        val newComment = com.tamastudy.tama.entity.Comment().apply {
            this.text = commentCreateRequest.text
            this.user = userEntity
            this.board = boardEntity
        }
        return commentMapper.toFlatDto(commentRepository.save(newComment))
    }

    @Caching(evict = [
        CacheEvict(value = ["board"], key = "#boardId"),
        CacheEvict(value = ["comments"], allEntries = true)
    ])
    override fun update(boardId: Long, commentId: Long, userDto: User.UserDto, commentUpdateRequest: Comment.CommentUpdateRequest): CommentFlatDto {
        val comment = commentRepository.findById(commentId)
        if (!comment.isPresent) throw NotFoundException("찾을 수 없습니다.")
        if (comment.get().user?.id != userDto.id) throw IllegalAccessException("권한이 없습니다.")
        val updateComment = comment.get().apply {
            this.text = commentUpdateRequest.text
        }
        return commentMapper.toFlatDto(commentRepository.save(updateComment))
    }

    @Caching(evict = [
        CacheEvict(value = ["board"], key = "#boardId"),
        CacheEvict(value = ["comments"], allEntries = true)
    ])
    override fun delete(boardId: Long, commentId: Long, userDto: User.UserDto) {
        val comment = commentRepository.findById(commentId)
        if (!comment.isPresent) throw NotFoundException("찾을 수 없습니다.")
        if (comment.get().user?.id != userDto.id) throw IllegalAccessException("권한이 없습니다.")
        commentRepository.deleteById(commentId)
    }

    @Cacheable(value = ["comments"], key = "#pageable")
    override fun searchPageDto(boardId: Long, pageable: Pageable): Page<CommentFlatDto> {
        return commentRepository.searchPageDto(boardId, pageable)
    }

    @Cacheable(value = ["comments"], key = "#boardId")
    override fun findAll(boardId: Long): List<CommentFlatDto> {
        return commentRepository.findAllFlatDto(boardId)
    }
}