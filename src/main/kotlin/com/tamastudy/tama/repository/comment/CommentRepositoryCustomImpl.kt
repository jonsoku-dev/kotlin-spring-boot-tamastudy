package com.tamastudy.tama.repository.comment

import com.querydsl.jpa.impl.JPAQueryFactory
import com.tamastudy.tama.dto.CommentFlatDto
import com.tamastudy.tama.dto.CommentResponseDto
import com.tamastudy.tama.entity.QComment.comment
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
    override fun testing(boardId: Long) {
        queryFactory.selectFrom(comment)
                .leftJoin(comment.superComment).fetchJoin()
                .where(comment.board.id.eq(boardId))
                .orderBy(
                        comment.superComment.id.asc().nullsFirst(),
                        comment.createdAt.asc()
                ).fetch()

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

    override fun findAllDto(boardId: Long): List<CommentResponseDto> {
        val entities = queryFactory.selectFrom(comment)
                .leftJoin(comment.superComment).fetchJoin()
                .where(comment.board.id.eq(boardId), comment.level.eq(1))
                .orderBy(
                        comment.superComment.id.desc().nullsFirst(),
                        comment.createdAt.desc()
                ).fetch()
        return commentMapper.toResponseDtos(entities)
    }
}