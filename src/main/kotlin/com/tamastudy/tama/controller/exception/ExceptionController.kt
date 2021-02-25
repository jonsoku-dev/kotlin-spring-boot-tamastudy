package com.tamastudy.tama.controller.exception

import org.springframework.web.bind.annotation.GetMapping

import org.springframework.web.bind.annotation.RequestMapping

import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping(value = ["/exception"])
class ExceptionController {
    @GetMapping(value = ["/entrypoint"])
    fun entrypointException() {
        throw RuntimeException("접근금지!")
    }

    @GetMapping(value = ["/accessdenied"])
    fun accessdeniedException() {
        throw RuntimeException("접근금지!")
    }
}