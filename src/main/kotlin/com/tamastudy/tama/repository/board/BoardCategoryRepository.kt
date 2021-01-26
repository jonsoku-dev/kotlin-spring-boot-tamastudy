package com.tamastudy.tama.repository.board

import com.tamastudy.tama.entity.BoardCategory
import org.springframework.data.jpa.repository.JpaRepository

interface BoardCategoryRepository : JpaRepository<BoardCategory, Long> {
}