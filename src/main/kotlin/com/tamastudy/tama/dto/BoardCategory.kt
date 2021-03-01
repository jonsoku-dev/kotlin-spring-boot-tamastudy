package com.tamastudy.tama.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import org.hibernate.validator.constraints.Length
import java.io.Serializable
import java.time.LocalDateTime
import javax.validation.constraints.NotBlank

data class BoardCategoryDto(
        @JsonProperty(value = "categoryId")
        var id: Long,
        var name: String,
        @JsonIgnore
        var createdAt: LocalDateTime,
        @JsonIgnore
        var updatedAt: LocalDateTime,
) : Serializable {
    companion object {
        @JvmStatic
        private val serialVersionUID: Long = 1
    }
}

data class BoardCategoryCreateRequest(
        @field:NotBlank
        @field:Length(max = 10)
        var name: String
)

data class BoardCategoryUpdateRequest(
        @field:Length(max = 10)
        var name: String
)
