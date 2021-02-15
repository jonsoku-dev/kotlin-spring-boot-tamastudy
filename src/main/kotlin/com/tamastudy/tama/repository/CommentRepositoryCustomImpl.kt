package com.tamastudy.tama.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.tamastudy.tama.dto.Comment
import com.tamastudy.tama.dto.Comment.CommentDto
import com.tamastudy.tama.dto.Comment.CommentFlatDto
import com.tamastudy.tama.entity.QBoard
import com.tamastudy.tama.entity.QBoard.board
import com.tamastudy.tama.entity.QComment
import com.tamastudy.tama.entity.QComment.comment
import com.tamastudy.tama.entity.QUser
import com.tamastudy.tama.entity.QUser.user
import com.tamastudy.tama.mapper.CommentMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@Repository
class CommentRepositoryCustomImpl(
        private val commentMapper: CommentMapper,
        private val em: EntityManager
) : CommentRepositoryCustom {

    private val queryFactory: JPAQueryFactory = JPAQueryFactory(em)

    override fun findAllFlatDto(boardId: Long): List<CommentFlatDto> {
        val result = queryFactory
                .selectFrom(comment)
                .join(comment.user, user).fetchJoin()
                .where(comment.board.id.eq(boardId))
                .orderBy(comment.createdAt.desc())
                .fetch()

        return result.map {
            commentMapper.toFlatDto(it)
        }
    }

    override fun searchPageDto(boardId: Long, pageable: Pageable): Page<CommentFlatDto> {
        val result = queryFactory
                .selectFrom(comment)
                .join(comment.user, user).fetchJoin()
                .where(comment.board.id.eq(boardId))
                .offset(pageable.offset)
                .orderBy(comment.createdAt.desc())
                .limit(pageable.pageSize.toLong())
                .fetch()

        val content = result.map {
            commentMapper.toFlatDto(it)
        }

        val countQuery = queryFactory
                .selectFrom(comment)

        return PageableExecutionUtils.getPage(
                content,
                pageable
        ) {
            countQuery.fetchCount()
        }
    }
}