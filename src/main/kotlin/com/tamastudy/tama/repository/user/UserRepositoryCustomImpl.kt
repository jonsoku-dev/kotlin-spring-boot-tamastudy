package com.tamastudy.tama.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.tamastudy.tama.entity.QUser
import com.tamastudy.tama.entity.QUser.user
import com.tamastudy.tama.entity.User
import org.springframework.cache.annotation.Cacheable
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.EntityManager

@Repository
class UserRepositoryCustomImpl(
        private val em: EntityManager
) : UserRepositoryCustom {
    private val queryFactory: JPAQueryFactory = JPAQueryFactory(em)

    override fun findForJoinByEmail(email: String): User? {
        return queryFactory.selectFrom(user).where(user.email.eq(email)).fetchOne()
    }

    @Cacheable(value = ["user"], key = "#email")
    override fun findByEmail(email: String): User? {
        return queryFactory.selectFrom(user).where(user.email.eq(email)).fetchOne()
    }
}