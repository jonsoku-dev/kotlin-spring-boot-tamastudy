package com.tamastudy.tama.advice

import com.tamastudy.tama.advice.exception.NotSameRefreshTokenException
import com.tamastudy.tama.advice.exception.ValidateRefreshTokenException
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
        println("defaultException")
        e.printStackTrace()
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseService.handleFailResult("서버에러입니다."))
    }

    @ExceptionHandler(NotSameRefreshTokenException::class)
    fun userNotFoundException(request: HttpServletRequest?, e: NotSameRefreshTokenException): ResponseEntity<ErrorResult> {
        e.printStackTrace()
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseService.handleFailResult("유저를 찾을 수 없습니다."))
    }

    @ExceptionHandler(AccessDeniedException::class)
    fun accessDeniedException(request: HttpServletRequest?, e: AccessDeniedException): ResponseEntity<ErrorResult> {
        println("accessDeniedException")
        e.printStackTrace()
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseService.handleFailResult("Token 을 확인해주세요."))
    }

    @ExceptionHandler(ValidateRefreshTokenException::class)
    fun validateRefreshTokenException(request: HttpServletRequest?, e: ValidateRefreshTokenException): ResponseEntity<ErrorResult> {
        println("validateRefreshTokenException")
        e.printStackTrace()
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseService.handleFailResult("ValidateRefreshTokenException"))
    }

}