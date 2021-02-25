package com.tamastudy.tama.service.comment

import com.tamastudy.tama.dto.*
import com.tamastudy.tama.mapper.BoardMapper
import com.tamastudy.tama.mapper.CommentMapper
import com.tamastudy.tama.mapper.UserMapper
import com.tamastudy.tama.repository.comment.CommentRepository
import javassist.NotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.RuntimeException

@Service
@Transactional
class CommentServiceImpl(
        private val commentRepository: CommentRepository,
        private val userMapper: UserMapper,
        private val boardMapper: BoardMapper,
        private val commentMapper: CommentMapper
) : CommentService {
//    @Caching(evict = [
//        CacheEvict(value = ["board"], key = "#boardDto.id"),
//        CacheEvict(value = ["comments"], allEntries = true)
//    ])
    override fun save(boardDto: BoardDto, userDto: UserDto, commentCreateRequest: CommentCreateRequest): CommentDto {
        val userEntity = userMapper.toEntity(userDto)
        val boardEntity = boardMapper.toEntity(boardDto)
        val newComment = com.tamastudy.tama.entity.Comment()
        if (commentCreateRequest.commentId == null) {
            newComment.level = 1
        } else {
            val supCommentId = commentCreateRequest.commentId
            val supComment = commentRepository.findByIdOrNull(supCommentId!!)
                    ?: throw NotFoundException("댓글을 찾을 수 없습니다.");
            if (supComment.isLive == false) {
                throw RuntimeException("SuperComment is already dead")
            }
            newComment.level = supComment.level!! + 1
            newComment.superComment = supComment
            supComment.subComment?.add(newComment)
        }

        newComment.text = commentCreateRequest.text
        newComment.board = boardEntity
        newComment.user = userEntity
        newComment.isLive = true

        return commentMapper.toDto(commentRepository.save(newComment))
    }

//    @Caching(evict = [
//        CacheEvict(value = ["board"], key = "#boardId"),
//        CacheEvict(value = ["comments"], allEntries = true)
//    ])
    override fun update(boardId: Long, commentId: Long, userDto: UserDto, commentUpdateRequest: CommentUpdateRequest): CommentFlatDto {
        val comment = commentRepository.findById(commentId)
        if (!comment.isPresent) throw NotFoundException("찾을 수 없습니다.")
        if (comment.get().user?.id != userDto.id) throw IllegalAccessException("권한이 없습니다.")
        val updateComment = comment.get().apply {
            this.text = commentUpdateRequest.text
        }
        return commentMapper.toFlatDto(commentRepository.save(updateComment))
    }

//    @Caching(evict = [
//        CacheEvict(value = ["board"], key = "#boardId"),
//        CacheEvict(value = ["comments"], allEntries = true)
//    ])
    override fun delete(boardId: Long, commentId: Long, userDto: UserDto) {
        val comment = commentRepository.findById(commentId)
        if (!comment.isPresent) throw NotFoundException("찾을 수 없습니다.")
        if (comment.get().user?.id != userDto.id) throw IllegalAccessException("권한이 없습니다.")
        commentRepository.deleteById(commentId)
    }

//    @Cacheable(value = ["comments"], key = "#pageable")
    override fun searchPageDto(boardId: Long, pageable: Pageable): Page<CommentFlatDto> {
        return commentRepository.searchPageDto(boardId, pageable)
    }

//    @Cacheable(value = ["comments"], key = "#boardId")
    override fun findAllFlatDto(boardId: Long): List<CommentFlatDto> {
        return commentRepository.findAllFlatDto(boardId)
    }

    //    @Cacheable(value = ["comments"], key = "#boardId")
    override fun findAllDto(boardId: Long): List<CommentResponseDto> {
        return commentRepository.findAllDto(boardId)
    }
}