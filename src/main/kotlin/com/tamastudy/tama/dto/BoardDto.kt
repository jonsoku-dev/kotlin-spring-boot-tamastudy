package com.tamastudy.tama.dto

import com.querydsl.core.annotations.QueryProjection

class BoardDto {
    data class BoardInfo(
            var id: Long? = null,
            var title: String? = null,
            var description: String? = null,
            var categoryId: Long? = null,
            var userId: Long? = null,
            var username: String? = null,
            var email: String? = null,
            var createdDate: String? = null
    )

    data class BoardCreateRequest(
            var title: String,
            var description: String,
            var categoryId: Long
    )

    data class BoardUpdateRequest(
            var title: String,
            var description: String,
            var categoryId: Long
    )

    data class BoardPagingCondition(
            var title: String? = null,
            var username: String? = null,
    )

    data class BoardPaging @QueryProjection constructor(
            var boardId: Long? = null,
            var title: String? = null,
            var description: String? = null,
            var userId: Long? = null,
            var username: String? = null,
            var email: String? = null,
            var boardCategoryId: Long? = null,
            var boardCategoryName: String? = null
    )
}