package com.tamastudy.tama.repository

import com.tamastudy.tama.entity.Board
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface BoardRepository : JpaRepository<Board, Long>, BoardRepositoryCustom {
    @EntityGraph(attributePaths = ["user", "category"])
    override fun findById(id: Long): Optional<Board>
}