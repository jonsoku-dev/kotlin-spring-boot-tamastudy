package com.tamastudy.tama.dto

class BoardCategoryDto {
    data class BoardCategoryInfo(
            var id: Long? = null,
            var name: String? = null,
    )

    data class BoardCategoryCreateRequest(
            var name: String? = null
    )
}