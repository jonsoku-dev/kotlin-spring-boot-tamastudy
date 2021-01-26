package com.tamastudy.tama.dto.board

import com.querydsl.core.annotations.QueryProjection


data class BoardPagingDto @QueryProjection constructor(
        var boardId: Long? = null,
        var title: String? = null,
        var description: String? = null,
        var userId: Long? = null,
        var username: String? = null,
        var email: String? = null,
        var boardCategoryId: Long? = null,
        var boardCategoryName: String? = null
)