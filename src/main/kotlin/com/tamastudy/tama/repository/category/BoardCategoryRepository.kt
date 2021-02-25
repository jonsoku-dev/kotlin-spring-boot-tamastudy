package com.tamastudy.tama.repository.category

import com.tamastudy.tama.entity.BoardCategory
import org.springframework.data.jpa.repository.JpaRepository

interface BoardCategoryRepository : JpaRepository<BoardCategory, Long> {
}