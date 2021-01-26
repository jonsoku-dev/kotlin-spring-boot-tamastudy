package com.tamastudy.tama.controller

import com.google.gson.Gson
import com.tamastudy.tama.dto.user.CreateUserRequest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
internal class RestApiControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Nested
    @DisplayName("join()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class Join {
        @Test
        fun `should created user`() {
            // given
            val gson = Gson()
            val testUser = CreateUserRequest().apply {
                this.username = "test"
                this.email = "test@gmail.com"
                this.password = "1234"
            }
            val json = gson.toJson(testUser)

            // when/then
            mockMvc.post("/api/v1/join") {
                contentType = MediaType.APPLICATION_JSON
                content = json
            }.andExpect {
                status {
                    isCreated()
                }
            }
        }
    }
}