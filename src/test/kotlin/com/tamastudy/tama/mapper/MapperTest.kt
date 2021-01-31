package com.tamastudy.tama.mapper

import com.tamastudy.tama.entity.Board
import com.tamastudy.tama.entity.BoardCategory
import com.tamastudy.tama.entity.User
import org.junit.jupiter.api.*

class MapperTest {
    @Nested
    @DisplayName("Mapper Test")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class mapperTest {
        @Test
        fun `Board_to_BoardDto`() {
            // given
            val testCategory = BoardCategory().apply {
                this.name = "category1"
            }

            val testUser = User().apply {
                this.email = "test@gmail.com"
                this.username = "jonsoku"
                this.password = "1234"
                this.roles = "ROLE_USER"
            }

            val testBoard = Board().apply {
                this.title = "test title"
                this.description = "test description"
                this.category = testCategory
                this.user = testUser
            }

            // when
            val boardDto = BoardMapper.MAPPER.toDto(testBoard)
            val boardCategoryDto = BoardCategoryMapper.MAPPER.toDto(testCategory)
            val userDto = UserMapper.MAPPER.toDto(testUser)

            // then
            Assertions.assertEquals(boardDto.title, "test title")
            Assertions.assertEquals(boardDto.description, "test description")
            Assertions.assertEquals(boardDto.category, boardCategoryDto)
            Assertions.assertEquals(boardDto.user, userDto)

        }
    }
}