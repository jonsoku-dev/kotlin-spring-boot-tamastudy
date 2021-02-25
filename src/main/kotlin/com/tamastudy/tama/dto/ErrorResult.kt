package com.tamastudy.tama.dto

import java.io.Serializable

data class ErrorResult(
        var success: Boolean? = null,
        var msg: String? = null,
) : Serializable