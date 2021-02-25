package com.tamastudy.tama.repository.user

import com.querydsl.jpa.impl.JPAQueryFactory
import com.tamastudy.tama.entity.QUser.user
import com.tamastudy.tama.entity.User
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@Repository
class UserRepositoryCustomImpl(
        private val em: EntityManager
) : UserRepositoryCustom {
    private val queryFactory: JPAQueryFactory = JPAQueryFactory(em)

    override fun findForJoinByEmail(email: String): User? {
        return queryFactory.selectFrom(user).where(user.email.eq(email)).fetchOne()
    }

    override fun findByEmail(email: String): User? {
        return queryFactory.selectFrom(user).where(user.email.eq(email)).fetchOne()
    }
}