package com.tamastudy.tama.mapper

import com.tamastudy.tama.entity.Board
import com.tamastudy.tama.entity.User
import com.tamastudy.tama.repository.BoardRepository
import com.tamastudy.tama.repository.UserRepository
import org.mapstruct.AfterMapping
import org.mapstruct.BeforeMapping
import org.mapstruct.MappingTarget
import javax.persistence.EntityManager


class JpaContext(
        private val boardRepository: BoardRepository,
) {
    private var user: User? = null

    @BeforeMapping
    fun setEntity(@MappingTarget user: User) {
        this.user = user
        // you could do stuff with the EntityManager here
    }

    @AfterMapping
    fun establishRelation(@MappingTarget board: Board) {
        val found = boardRepository.findOneWithUserById(board.id!!)
        board.user = found?.user
    }
}