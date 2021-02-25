package com.tamastudy.tama.dto

import java.time.LocalDateTime

data class ErrorResponse(
        var message: String? = null,
        var timestamp: LocalDateTime? = null,
)
