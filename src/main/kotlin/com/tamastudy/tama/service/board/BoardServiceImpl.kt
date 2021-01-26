package com.tamastudy.tama.service.board

import com.tamastudy.tama.dto.board.*
import com.tamastudy.tama.entity.*
import com.tamastudy.tama.repository.board.BoardCategoryRepository
import com.tamastudy.tama.repository.board.BoardRepository
import com.tamastudy.tama.repository.user.UserRepository
import javassist.NotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.function.Supplier

@Service
class BoardServiceImpl(
        private val repository: BoardRepository
) : BoardService {
    override fun findAllWithPage(condition: BoardPagingCondition?, pageable: Pageable?): Page<BoardPagingDto> {
        return repository.searchPageSimple(condition, pageable)
    }

    override fun findAllWithComplexPage(condition: BoardPagingCondition?, pageable: Pageable?): Page<BoardPagingDto> {
        return repository.searchPageComplex(condition, pageable)
    }

    override fun findById(id: Long): BoardDto {
        return findBoard(id).let {
            BoardDto().convertBoardDto(it)
        }
    }

    override fun createBoard(board: Board): BoardDto {
        return BoardDto().convertBoardDto(repository.save(board))
    }

    override fun updateBoard(board: Board): BoardDto {
        val newBoard = board.id?.let {
            findBoard(it).let {
                repository.save(board)
            }
        } ?: throw NotFoundException("${board.id} 에 해당하는 게시물을 찾을 수 없습니다.")

        return BoardDto().convertBoardDto(newBoard)
    }

    override fun deleteById(id: Long) {
        findBoard(id).let {
            repository.delete(it)
        }
    }

    private fun findBoard(id: Long): Board {
        return repository.findByIdOrNull(id) ?: throw NotFoundException("$id 에 해당하는 게시물을 찾을 수 없습니다.")
    }

}