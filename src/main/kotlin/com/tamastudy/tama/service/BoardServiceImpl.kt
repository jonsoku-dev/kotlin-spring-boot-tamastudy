package com.tamastudy.tama.service

import com.tamastudy.tama.dto.Board.*
import com.tamastudy.tama.dto.BoardCategory.BoardCategoryDto
import com.tamastudy.tama.dto.User.UserDto
import com.tamastudy.tama.entity.Board
import com.tamastudy.tama.mapper.BoardCategoryMapper
import com.tamastudy.tama.mapper.BoardMapper
import com.tamastudy.tama.mapper.UserMapper
import com.tamastudy.tama.repository.BoardRepository
import javassist.NotFoundException
import org.springframework.cache.annotation.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class BoardServiceImpl(
        private val userMapper: UserMapper,
        private val boardMapper: BoardMapper,
        private val boardCategoryMapper: BoardCategoryMapper,
        private val repository: BoardRepository,
) : BoardService {
    override fun findAllWithComplexPage(condition: BoardPagingCondition, pageable: Pageable): Page<BoardPaging> {
        return repository.searchPageComplex(condition, pageable)
    }

    @Cacheable(value = ["boards"], keyGenerator = "boardCacheGenerator")
    override fun findDtosWithComplexPage(condition: BoardPagingCondition, pageable: Pageable): Page<BoardFlatDto> {
        return repository.searchPageDto(condition, pageable)
    }

    @Cacheable(value = ["board"], key = "#id")
    override fun findById(id: Long): BoardFlatDto {
        val foundBoard = findBoard(id)
        return boardMapper.toFlatDto(foundBoard)
    }

    override fun retrieveById(id: Long): BoardDto {
        val foundBoard = repository.findById(id)
        println("foundBoard: $foundBoard")
        if (!foundBoard.isPresent) throw NotFoundException("찾을 수 없어요")
        return boardMapper.toDto(foundBoard.get())
    }

    @CacheEvict(value = ["boards"], allEntries = true)
    override fun createBoard(userDto: UserDto, categoryDto: BoardCategoryDto, createBoardCreateRequest: BoardCreateRequest): BoardFlatDto {
        val newBoard = Board().apply {
            this.title = createBoardCreateRequest.title
            this.description = createBoardCreateRequest.description
            this.user = userMapper.toEntity(userDto)
            this.category = boardCategoryMapper.toEntity(categoryDto)
        }
        val savedBoard = repository.save(newBoard)

        return boardMapper.toFlatDto(savedBoard)
    }

    @Caching(evict = [
        CacheEvict(value = ["boards"], allEntries = true),
        CacheEvict(value = ["board"], key = "#boardId")
    ])
    override fun updateBoard(boardId: Long, userDto: UserDto, categoryDto: BoardCategoryDto, boardUpdateRequest: BoardUpdateRequest): BoardFlatDto {
        val foundBoard = findBoard(boardId)

        if (foundBoard.user!! != userMapper.toEntity(userDto)) {
            throw IllegalAccessException("권한이 없습니다.")
        }

        val updateBoard = foundBoard.apply {
            this.title = boardUpdateRequest.title
            this.description = boardUpdateRequest.description
            this.category = boardCategoryMapper.toEntity(categoryDto)
        }

        return boardMapper.toFlatDto(repository.save(updateBoard))
    }

    @Caching(evict = [
        CacheEvict(value = ["boards"], allEntries = true),
        CacheEvict(value = ["board"], key = "#boardId")
    ])
    override fun deleteById(boardId: Long, userDto: UserDto) {
        findBoard(boardId).let { board ->
            if (board.user?.id != userMapper.toEntity(userDto).id) {
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