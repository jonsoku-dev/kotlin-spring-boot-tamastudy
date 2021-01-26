package com.tamastudy.tama.dto.board

data class CreateBoardRequest(
        var title: String,
        var description: String,
        var categoryId: Long
)