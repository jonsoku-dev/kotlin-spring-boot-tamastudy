package com.tamastudy.tama.repository

import com.tamastudy.tama.entity.Comment
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CommentRepository : JpaRepository<Comment, Long>, CommentRepositoryCustom {
    @EntityGraph(attributePaths = ["user", "board"])
    override fun findById(id: Long): Optional<Comment>
}