package com.tamastudy.tama.repository.user

import com.querydsl.jpa.impl.JPAQueryFactory
import com.tamastudy.tama.entity.QUser.user
import com.tamastudy.tama.entity.User
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@Repository
class UserRepositoryCustomImpl(
    private val queryFactory: JPAQueryFactory
) : UserRepositoryCustom {

    override fun findForJoinByEmail(email: String): User? {
        return queryFactory.selectFrom(user).where(user.email.eq(email)).fetchOne()
    }

    override fun findByEmail(email: String): User? {
        return queryFactory.selectFrom(user).where(user.email.eq(email)).fetchOne()
    }
}