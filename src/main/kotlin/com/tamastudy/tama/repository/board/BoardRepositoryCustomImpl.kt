package com.tamastudy.tama.repository.board

import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.impl.JPAQueryFactory
import com.tamastudy.tama.dto.*
import com.tamastudy.tama.entity.Board
import com.tamastudy.tama.entity.QBoard.board
import com.tamastudy.tama.entity.QBoardCategory.boardCategory
import com.tamastudy.tama.entity.QUser.user
import com.tamastudy.tama.mapper.BoardMapper
import org.springframework.data.domain.*
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@Repository
class BoardRepositoryCustomImpl(
        private val boardMapper: BoardMapper,
        private val em: EntityManager
) : BoardRepositoryCustom {

    private val queryFactory: JPAQueryFactory = JPAQueryFactory(em)

    override fun findIds(): MutableList<BoardIds> {
        return queryFactory
                .select(QBoardIds(
                        board.id.`as`("boardId")
                ))
                .from(board)
                .fetch()
    }

    override fun searchPageSimple(condition: BoardPagingCondition, pageable: Pageable): Page<BoardPaging> {
        val result = queryFactory
                .select(QBoardPaging(
                        board.id.`as`("boardId"),
                        board.title,
                        board.description,
                        user.id.`as`("userId"),
                        user.username,
                        user.email,
                        boardCategory.id.`as`("boardCategoryId"),
                        boardCategory.name.`as`("boardCategoryName"),
                ))
                .from(board)
                .leftJoin(board.user, user)
                .leftJoin(board.category, boardCategory)
                .offset(pageable.offset)
                .limit(pageable.pageSize.toLong())
                .fetchResults()

        val content = result.results
        val total = result.total

        return PageImpl(content, pageable, total)
    }

    override fun searchPageComplex(condition: BoardPagingCondition, pageable: Pageable): Page<BoardPaging> {
        val content = queryFactory
                .select(QBoardPaging(
                        board.id.`as`("boardId"),
                        board.title,
                        board.description,
                        user.id.`as`("userId"),
                        user.username,
                        user.email,
                        boardCategory.id.`as`("boardCategoryId"),
                        boardCategory.name.`as`("boardCategoryName"),
                ))
                .from(board)
                .leftJoin(board.user, user)
                .leftJoin(board.category, boardCategory)
                .offset(pageable.offset)
                .limit(pageable.pageSize.toLong())
                .fetch()

        val countQuery = queryFactory
                .select(board)
                .from(board)
                .leftJoin(board.user, user)
                .leftJoin(board.category, boardCategory)

        return PageableExecutionUtils.getPage(
                content,
                pageable
        ) {
            countQuery.fetchCount()
        }
    }

    override fun findOneWithUserById(boardId: Long): Board? {
        return queryFactory
                .select(board)
                .from(board)
                .join(board.user, user).fetchJoin()
                .join(board.category, boardCategory).fetchJoin()
                .where(board.id.eq(boardId))
                .fetchOne()
    }

    override fun searchPageDto(condition: BoardPagingCondition, pageable: Pageable): Page<BoardFlatDto> {
        val test = queryFactory
                .select(board)
                .from(board)
                .join(board.user, user).fetchJoin()
                .join(board.category, boardCategory).fetchJoin()
                .where(categoryNameEq(condition.categoryName), keywordLike(condition.keyword))
                .offset(pageable.offset)
                .orderBy(board.createdAt.desc())
                .limit(pageable.pageSize.toLong())
                .fetch()


        val content = test.map { board ->
            boardMapper.toFlatDto(board)
        }

        val countQuery = queryFactory
                .select(board)
                .from(board)
                .leftJoin(board.user, user)
                .leftJoin(board.category, boardCategory)
                .where(categoryNameEq(condition.categoryName), keywordLike(condition.keyword))

        return PageableExecutionUtils.getPage(
                content,
                pageable
        ) {
            countQuery.fetchCount()
        }
    }

    override fun searchSliceDto(condition: BoardPagingCondition, pageable: Pageable): Slice<BoardFlatDto> {
        val test = queryFactory
                .select(board)
                .from(board)
                .join(board.user, user).fetchJoin()
                .join(board.category, boardCategory).fetchJoin()
                .where(categoryNameEq(condition.categoryName), keywordLike(condition.keyword))
                .offset(pageable.offset)
                .orderBy(board.createdAt.desc())
                .limit(pageable.pageSize.toLong())
                .fetch()


        val countQuery = queryFactory
                .select(board)
                .from(board)
                .leftJoin(board.user, user)
                .leftJoin(board.category, boardCategory)
                .where(categoryNameEq(condition.categoryName), keywordLike(condition.keyword))

        val content = test.map { board ->
            boardMapper.toFlatDto(board)
        }

        return PageableExecutionUtils.getPage(
                content,
                pageable
        ) {
            countQuery.fetchCount()
        }
    }

    private fun keywordLike(keyword: String?): BooleanExpression? {
        if (keyword == null) return null;
        return board.title.like(Expressions.asString("%").concat(keyword).concat("%"))
    }

    private fun categoryNameEq(categoryName: String?): BooleanExpression? {
        if (categoryName == null) return null;
        return board.category.name.eq(categoryName);
    }

}