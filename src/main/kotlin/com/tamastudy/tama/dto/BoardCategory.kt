package com.tamastudy.tama.dto

import org.hibernate.validator.constraints.Length
import java.time.LocalDateTime
import javax.validation.constraints.NotBlank

class BoardCategory {
    data class BoardCategoryDto(
            var id: Long? = null,
            var name: String? = null,
            var createdAt: LocalDateTime? = null,
            var updatedAt: LocalDateTime? = null,
    )

    data class BoardCategoryCreateRequest(
            @field:NotBlank
            @field:Length(max = 10)
            var name: String? = null
    )

    data class BoardCategoryUpdateRequest(
            @field:Length(max = 10)
            var name: String? = null
    )
}