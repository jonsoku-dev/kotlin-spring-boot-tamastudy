package com.tamastudy.tama.service

import com.tamastudy.tama.dto.BoardDto
import com.tamastudy.tama.dto.BoardDto.BoardInfo
import com.tamastudy.tama.dto.BoardDto.BoardPagingCondition
import com.tamastudy.tama.entity.Board
import com.tamastudy.tama.mapper.BoardMapper
import com.tamastudy.tama.repository.BoardRepository
import javassist.NotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class BoardServiceImpl(
        private val boardMapper: BoardMapper,
        private val repository: BoardRepository
) : BoardService {
    override fun findAllWithComplexPage(condition: BoardPagingCondition, pageable: Pageable): Page<BoardDto.BoardPaging> {
        return repository.searchPageComplex(condition, pageable)
    }

    override fun findById(id: Long): BoardInfo {
        return findBoard(id).let {
            boardMapper.toDto(it)
        }
    }

    override fun createBoard(board: Board): BoardInfo {
        return boardMapper.toDto(repository.save(board))
    }

    override fun updateBoard(board: Board): BoardInfo {
        val newBoard = board.id?.let {
            findBoard(it).let {
                repository.save(board)
            }
        } ?: throw NotFoundException("${board.id} 에 해당하는 게시물을 찾을 수 없습니다.")
        return boardMapper.toDto(newBoard)
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