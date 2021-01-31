package com.tamastudy.tama.service

import com.tamastudy.tama.dto.BoardCategory.*
import com.tamastudy.tama.entity.BoardCategory
import com.tamastudy.tama.mapper.BoardCategoryMapper
import com.tamastudy.tama.repository.BoardCategoryRepository
import javassist.NotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class BoardCategoryServiceImpl(
        private val repository: BoardCategoryRepository
) : BoardCategoryService {
    override fun createCategory(boardCategoryCreateRequest: BoardCategoryCreateRequest): BoardCategoryDto {
        val newBoardCategory = BoardCategory().apply {
            this.name = boardCategoryCreateRequest.name
        }
        return BoardCategoryMapper.MAPPER.toDto(repository.save(newBoardCategory))
    }

    override fun updateCategory(id: Long, boardCategoryUpdateRequest: BoardCategoryUpdateRequest): BoardCategoryDto {
        val updatedCategory = findCategory(id).apply {
            this.name = boardCategoryUpdateRequest.name
        }
        return BoardCategoryMapper.MAPPER.toDto(repository.save(updatedCategory))
    }

    override fun findById(id: Long): BoardCategoryDto {
        return BoardCategoryMapper.MAPPER.toDto(findCategory(id))
    }

    override fun findAll(): List<BoardCategoryDto> {
        return BoardCategoryMapper.MAPPER.toDtos(repository.findAll())
    }

    override fun deleteById(id: Long) {
        return repository.delete(findCategory(id))
    }

    private fun findCategory(id: Long): BoardCategory {
        return repository.findByIdOrNull(id) ?: throw NotFoundException("$id 에 해당하는 카테고리를 찾을 수 없습니다.")
    }
}