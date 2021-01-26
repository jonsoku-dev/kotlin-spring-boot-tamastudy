package com.tamastudy.tama.repository

import com.tamastudy.tama.entity.Board
import org.springframework.data.jpa.repository.JpaRepository

interface BoardRepository : JpaRepository<Board, Long>, BoardRepositoryCustom {}