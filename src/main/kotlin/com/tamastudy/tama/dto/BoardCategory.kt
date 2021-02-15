package com.tamastudy.tama.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import org.hibernate.validator.constraints.Length
import java.io.Serializable
import java.time.LocalDateTime
import javax.validation.constraints.NotBlank

class BoardCategory {
    data class BoardCategoryDto(
            @JsonProperty(value = "categoryId")
            var id: Long? = null,
            var name: String? = null,
            @JsonIgnore
            var createdAt: LocalDateTime? = null,
            @JsonIgnore
            var updatedAt: LocalDateTime? = null,
    ) : Serializable {
        companion object {
            @JvmStatic
            private val serialVersionUID: Long = 1
        }
    }

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