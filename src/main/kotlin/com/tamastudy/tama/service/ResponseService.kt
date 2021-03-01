package com.tamastudy.tama.service

import com.tamastudy.tama.dto.ErrorResult
import org.springframework.stereotype.Service

@Service
class ResponseService {
    // 실패 결과 처리
    fun handleFailResult(msg: String): ErrorResult = ErrorResult(
        false,
        msg
    )
}