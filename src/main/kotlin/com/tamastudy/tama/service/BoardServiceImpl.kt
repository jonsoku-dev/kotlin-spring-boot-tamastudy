package com.tamastudy.tama.service

import com.tamastudy.tama.dto.Board.*
import com.tamastudy.tama.entity.Board
import com.tamastudy.tama.entity.BoardCategory
import com.tamastudy.tama.entity.User
import com.tamastudy.tama.mapper.BoardCategoryMapper
import com.tamastudy.tama.mapper.BoardMapper
import com.tamastudy.tama.repository.BoardRepository
import javassist.NotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class BoardServiceImpl(
        private val repository: BoardRepository,
) : BoardService {
    override fun findAllWithComplexPage(condition: BoardPagingCondition, pageable: Pageable): Page<BoardPaging> {
        return repository.searchPageComplex(condition, pageable)
    }

    override fun findDtosWithComplexPage(condition: BoardPagingCondition, pageable: Pageable): Page<BoardDto> {
        return repository.searchPageDto(condition, pageable)
    }

    override fun findById(id: Long): BoardDto {
        val foundBoard = findBoard(id)
        return BoardMapper.MAPPER.toDto(foundBoard)
    }

    override fun createBoard(board: Board): BoardDto {
        return BoardMapper.MAPPER.toDto(repository.save(board))
    }

    override fun updateBoard(boardId: Long, user: User, category: BoardCategory, boardUpdateRequest: BoardUpdateRequest): BoardDto {

        val foundBoard = findBoard(boardId)

        if (foundBoard.user!! != user) {
            throw IllegalAccessException("권한이 없습니다.")
        }

        println("foundBoard : $foundBoard")

        val updateBoard = foundBoard.apply {
            this.title = boardUpdateRequest.title
            this.description = boardUpdateRequest.description
            this.category = category
        }

        println("updateBoard : $updateBoard")

        return BoardMapper.MAPPER.toDto(repository.save(updateBoard))
    }

    override fun deleteById(user: User, boardId: Long) {
        findBoard(boardId).let { board ->
            if (board.user?.id != user.id) {
                throw IllegalAccessException("권한이 없습니다.")
            }
            repository.delete(board)
        }
    }

    private fun findBoard(id: Long): Board {
        return repository.findById(id).let {
            if (it.isPresent) {
                it.get()
            } else {
                throw NotFoundException("$id 에 해당하는 게시물을 찾을 수 없습니다.")
            }
        }
    }

}