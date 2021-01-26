package com.tamastudy.tama

import com.tamastudy.tama.entity.Board
import com.tamastudy.tama.entity.BoardCategory
import com.tamastudy.tama.entity.User
import com.tamastudy.tama.mapper.BoardMapper
import com.tamastudy.tama.mapper.BoardMapperImpl
import com.tamastudy.tama.service.board.BoardService
import com.tamastudy.tama.service.board.BoardServiceImpl
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext

@SpringBootTest(classes = [ApplicationContext::class, BoardMapperImpl::class])
class MapperTest @Autowired constructor(
        val boardMapper: BoardMapper,
        val boardService: BoardService
) {
    @Test
    fun `should success`() {
        // given
        val userEntity = User().apply {
            this.email = "test@gmail.com"
            this.password = "1234"
            this.username = "test"
            this.roles = "ROLE_USER"
        }

        val categoryEntity = BoardCategory().apply {
            this.name = "category1"
        }

        val boardEntity = Board().apply {
            this.title = "title"
            this.description = "description"
            this.category = categoryEntity
            this.user = userEntity
        }

        val boardDto = boardService.createBoard(boardEntity)
        println("DTO :: $boardDto")
        val board = boardMapper.toEntity(boardDto)
        println("ENTITY :: $board")
    }
}