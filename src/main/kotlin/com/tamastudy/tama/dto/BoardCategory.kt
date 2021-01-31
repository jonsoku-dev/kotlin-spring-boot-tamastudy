package com.tamastudy.tama.dto

import java.time.Instant
import java.time.LocalDateTime

class BoardCategory {
    data class BoardCategoryDto(
            var id: Long? = null,
            var name: String? = null,
            var createdAt: LocalDateTime? = null,
            var updatedAt: LocalDateTime? = null,
    )

    data class BoardCategoryInfo(
            var id: Long? = null,
            var name: String? = null,
    )

    data class BoardCategoryCreateRequest(
            var name: String? = null
    )

    data class BoardCategoryUpdateRequest(
            var name: String? = null
    )
}