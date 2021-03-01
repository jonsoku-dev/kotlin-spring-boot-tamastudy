package com.tamastudy.tama.advice

import com.tamastudy.tama.advice.exception.UserNotFoundException
import com.tamastudy.tama.dto.ErrorResult
import com.tamastudy.tama.service.ResponseService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.servlet.http.HttpServletRequest


@RestControllerAdvice
class GlobalExceptionAdvice(
        private val responseService: ResponseService,
) {
    @ExceptionHandler(Exception::class)
    fun defaultException(request: HttpServletRequest?, e: Exception): ResponseEntity<ErrorResult> {
        println(e.localizedMessage)
        e.stackTrace
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseService.handleFailResult("defaultException"))
    }

    @ExceptionHandler(UserNotFoundException::class)
    fun userNotFoundException(request: HttpServletRequest?, e: UserNotFoundException): ResponseEntity<ErrorResult> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseService.handleFailResult("userNotFoundException"))
    }

    @ExceptionHandler(AccessDeniedException::class)
    fun accessDeniedException(request: HttpServletRequest?, e: AccessDeniedException): ResponseEntity<ErrorResult> {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseService.handleFailResult("accessDeniedException"))
    }
}