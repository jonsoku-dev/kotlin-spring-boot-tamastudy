package com.tamastudy.tama.dto.board

data class UpdateBoardRequest(
        var title: String? = null,
        var description: String? = null,
        var categoryId: Long? = null
)