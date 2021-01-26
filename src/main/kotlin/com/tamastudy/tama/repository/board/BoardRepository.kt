package com.tamastudy.tama.repository.board

import com.tamastudy.tama.entity.Board
import com.tamastudy.tama.entity.User
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface BoardRepository : JpaRepository<Board, Long>, BoardRepositoryCustom {}