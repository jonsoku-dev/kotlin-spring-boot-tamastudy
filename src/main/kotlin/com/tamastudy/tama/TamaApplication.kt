package com.tamastudy.tama

import com.tamastudy.tama.entity.Board
import com.tamastudy.tama.entity.BoardCategory
import com.tamastudy.tama.entity.User
import com.tamastudy.tama.repository.BoardCategoryRepository
import com.tamastudy.tama.repository.BoardRepository
import com.tamastudy.tama.repository.UserRepository
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.filter.CharacterEncodingFilter
import javax.annotation.PostConstruct


@SpringBootApplication
@EnableCaching
class TamaApplication(
        private val categoryRepository: BoardCategoryRepository,
        private val userRepository: UserRepository,
        private val boardRepository: BoardRepository,
        private val bCryptPasswordEncoder: BCryptPasswordEncoder
) {
    @PostConstruct
    fun initUser() {
        val categories = listOf(
                BoardCategory().apply {
                    this.name = "React"
                },
                BoardCategory().apply {
                    this.name = "Vue"
                },
                BoardCategory().apply {
                    this.name = "Angular"
                }
        )
        val users = listOf(
                User().apply {
                    this.username = "test"
                    this.email = "test@gmail.com"
                    this.password = bCryptPasswordEncoder.encode("1234")
                    this.roles = "USER_ROLE"
                },
                User().apply {
                    this.username = "test2"
                    this.email = "test2@gmail.com"
                    this.password = bCryptPasswordEncoder.encode("1234")
                    this.roles = "USER_ROLE"
                },
                User().apply {
                    this.username = "test3"
                    this.email = "test3@gmail.com"
                    this.password = bCryptPasswordEncoder.encode("1234")
                    this.roles = "USER_ROLE"
                }
        )
        categoryRepository.saveAll(categories)
        userRepository.saveAll(users)

        val boards = mutableListOf<Board>()
        (0..100).forEach {
            boards.add(Board().apply {
                this.title = "random #$it"
                this.description = "random stringrandom stringrandom string"
                this.category = categoryRepository.findByIdOrNull(1L)
                this.user = userRepository.findByIdOrNull(1L)
            })
        }
        boardRepository.saveAll(boards)

    }
}

fun main(args: Array<String>) {
    runApplication<TamaApplication>(*args)
}
