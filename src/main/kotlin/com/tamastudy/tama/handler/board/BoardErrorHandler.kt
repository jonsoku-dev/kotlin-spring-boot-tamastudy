package com.tamastudy.tama.handler.board

import com.tamastudy.tama.controller.BoardApiController
import com.tamastudy.tama.dto.error.Error
import com.tamastudy.tama.dto.error.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.LocalDateTime
import javax.servlet.http.HttpServletRequest

@ControllerAdvice(basePackageClasses = [BoardApiController::class])
class BoardErrorHandler {
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception, request: HttpServletRequest): ResponseEntity<ErrorResponse> {

        val errors = mutableListOf<Error>()

        Error().apply {
            this.message = e.localizedMessage
        }.apply {
            errors.add(this)
        }

        return ResponseEntity.badRequest().body(createErrorResponse(request, errors))
    }

    private fun createErrorResponse(request: HttpServletRequest, errors: MutableList<Error>): ErrorResponse {
        return ErrorResponse().apply {
            this.resultCode = "FAIL"
            this.httpStatus = HttpStatus.BAD_REQUEST.value().toString()
            this.httpMethod = request.method
            this.path = request.requestURI
            this.timestamp = LocalDateTime.now()
            this.errors = errors
        }
    }
}