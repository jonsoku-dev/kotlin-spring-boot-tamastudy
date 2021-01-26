package com.tamastudy.tama.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

class ErrorDto {
    data class ErrorResponse(
            @field:JsonProperty("result_code")
            var resultCode: String? = null,
            @field:JsonProperty("http_status")
            var httpStatus: String? = null,
            @field:JsonProperty("http_method")
            var httpMethod: String? = null,
            var path: String? = null,
            var timestamp: LocalDateTime? = null,
            var errors: MutableList<Error>? = null
    )

    data class Error(
            var field: String? = null,
            var message: String? = null,
            var value: Any? = null
    )
}