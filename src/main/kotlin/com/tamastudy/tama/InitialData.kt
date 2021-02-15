package com.tamastudy.tama

import com.tamastudy.tama.entity.Board
import com.tamastudy.tama.entity.BoardCategory
import com.tamastudy.tama.entity.User
import com.tamastudy.tama.repository.BoardCategoryRepository
import com.tamastudy.tama.repository.BoardRepository
import com.tamastudy.tama.repository.UserRepository
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component


//@Component
class InitialData(
        private val bCryptPasswordEncoder: BCryptPasswordEncoder,
        private val userRepository: UserRepository,
        private val boardCategoryRepository: BoardCategoryRepository,
        private val boardRepository: BoardRepository
) : ApplicationRunner {
    override fun run(args: ApplicationArguments) {
        val user = userRepository.findByEmail("the2792@gmail.com")
        val category = boardCategoryRepository.findById(1L)

        (1..50).forEach {
            boardRepository.save<Board>(Board().apply {
                this.title = "test$it"
                this.description = "1234"
                this.user = user
                this.category = category.get()
            })
        }
    }
}