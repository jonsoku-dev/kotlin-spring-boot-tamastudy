package com.tamastudy.tama

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.web.filter.CharacterEncodingFilter


@SpringBootApplication
@EnableCaching
class TamaApplication

fun main(args: Array<String>) {
    runApplication<TamaApplication>(*args)
}
