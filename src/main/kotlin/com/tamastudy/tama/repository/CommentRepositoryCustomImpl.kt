package com.tamastudy.tama.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.tamastudy.tama.dto.Comment
import com.tamastudy.tama.dto.Comment.CommentDto
import com.tamastudy.tama.dto.Comment.CommentFlatDto
import com.tamastudy.tama.entity.QBoard
import com.tamastudy.tama.entity.QBoard.board
import com.tamastudy.tama.entity.QBoardCategory
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
import kotlin.math.log

@Repository
class CommentRepositoryCustomImpl(
        private val commentMapper: CommentMapper,
        private val em: EntityManager
) : CommentRepositoryCustom {

    private val queryFactory: JPAQueryFactory = JPAQueryFactory(em)
    override fun testing(boardId: Long) {
        val commentList = queryFactory
                .select(comment)
                .from(comment)
                .join(comment.user, user).fetchJoin()
                .where(comment.board.id.eq(boardId))
                .fetch()

        val result = commentList.map {
            commentMapper.toDto(it)
        }
        println(result)
    }

    override fun findAllFlatDto(boardId: Long): List<CommentFlatDto> {
        val superResult = queryFactory
                .select(comment)
                .from(comment)
                .where(comment.board.id.eq(boardId))
                .fetch()

        return superResult.map {
            commentMapper.toFlatDto(it)
        }
    }

    override fun findAllDto(boardId: Long): List<CommentDto> {
        val superResult = queryFactory
                .select(comment)
                .from(comment)
                .join(comment.user, user).fetchJoin()
                .join(comment.board, board).fetchJoin()
                .where(comment.board.id.eq(boardId), comment.level.eq(1), comment.superComment.isNull)
                .fetch()

        superResult.forEach {
            it.subComment = queryFactory
                    .select(comment)
                    .from(comment)
                    .join(comment.user, user).fetchJoin()
                    .join(comment.board, board).fetchJoin()
                    .where(comment.board.id.eq(boardId), comment.level.eq(2), comment.superComment.id.eq(it.id))
                    .fetch()
        }

        return superResult.map {
            commentMapper.toDto(it)
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