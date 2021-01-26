package com.tamastudy.tama.repository.board

import com.querydsl.jpa.impl.JPAQueryFactory
import com.tamastudy.tama.dto.board.BoardDto
import com.tamastudy.tama.dto.board.BoardPagingCondition
import com.tamastudy.tama.dto.board.BoardPagingDto
import com.tamastudy.tama.dto.board.QBoardPagingDto
import com.tamastudy.tama.entity.Board
import com.tamastudy.tama.entity.QBoard.board
import com.tamastudy.tama.entity.QBoardCategory.boardCategory
import com.tamastudy.tama.entity.QUser.user
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.EntityManager

@Repository
class BoardRepositoryCustomImpl(private val em: EntityManager) : BoardRepositoryCustom {
    private var queryFactory: JPAQueryFactory? = null

    init {
        queryFactory = JPAQueryFactory(em)
    }

    override fun searchPageSimple(condition: BoardPagingCondition?, pageable: Pageable?): Page<BoardPagingDto> {
        val result = queryFactory
                ?.select(QBoardPagingDto(
                        board.id.`as`("boardId"),
                        board.title,
                        board.description,
                        user.id.`as`("userId"),
                        user.username,
                        user.email,
                        boardCategory.id.`as`("boardCategoryId"),
                        boardCategory.name.`as`("boardCategoryName"),
                ))
                ?.from(board)
                ?.leftJoin(board.user, user)
                ?.leftJoin(board.category, boardCategory)
                ?.offset(pageable?.offset ?: 0)
                ?.limit(pageable?.pageSize!!.toLong())
                ?.fetchResults()

        val content = result?.results!!
        val total = result.total

        return PageImpl<BoardPagingDto>(content, pageable!!, total)
    }

    override fun searchPageComplex(condition: BoardPagingCondition?, pageable: Pageable?): Page<BoardPagingDto> {
        val content = queryFactory
                ?.select(QBoardPagingDto(
                        board.id.`as`("boardId"),
                        board.title,
                        board.description,
                        user.id.`as`("userId"),
                        user.username,
                        user.email,
                        boardCategory.id.`as`("boardCategoryId"),
                        boardCategory.name.`as`("boardCategoryName"),
                ))
                ?.from(board)
                ?.leftJoin(board.user, user)
                ?.leftJoin(board.category, boardCategory)
                ?.offset(pageable?.offset ?: 0)
                ?.limit(pageable?.pageSize!!.toLong())
                ?.fetch()

        val countQuery = queryFactory
                ?.select(board)
                ?.from(board)
                ?.leftJoin(board.user, user)
                ?.leftJoin(board.category, boardCategory)

        return PageableExecutionUtils.getPage(
                content!!,
                pageable!!
        ) {
            countQuery!!.fetchCount()
        }
    }

    override fun findOneWithUserById(boardId: Long): Board? {
        return queryFactory
                ?.select(board)
                ?.from(board)
                ?.join(board.user, user)?.fetchJoin()
                ?.join(board.category, boardCategory)?.fetchJoin()
                ?.where(board.id.eq(boardId))
                ?.fetchOne()
    }
}