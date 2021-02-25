package com.tamastudy.tama.service.category

import com.tamastudy.tama.dto.BoardCategoryCreateRequest
import com.tamastudy.tama.dto.BoardCategoryDto
import com.tamastudy.tama.dto.BoardCategoryUpdateRequest
import com.tamastudy.tama.entity.BoardCategory
import com.tamastudy.tama.mapper.BoardCategoryMapper
import com.tamastudy.tama.repository.category.BoardCategoryRepository
import javassist.NotFoundException
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class BoardCategoryServiceImpl(
        private val boardCategoryMapper: BoardCategoryMapper,
        private val repository: BoardCategoryRepository
) : BoardCategoryService {
    override fun createCategory(boardCategoryCreateRequest: BoardCategoryCreateRequest): BoardCategoryDto {
        val newBoardCategory = BoardCategory().apply {
            this.name = boardCategoryCreateRequest.name
        }
        return boardCategoryMapper.toDto(repository.save(newBoardCategory))
    }

    override fun updateCategory(id: Long, boardCategoryUpdateRequest: BoardCategoryUpdateRequest): BoardCategoryDto {
        val updatedCategory = findCategory(id).apply {
            this.name = boardCategoryUpdateRequest.name
        }
        return boardCategoryMapper.toDto(repository.save(updatedCategory))
    }

    override fun findById(id: Long): BoardCategoryDto {
        return boardCategoryMapper.toDto(findCategory(id))
    }

    @Cacheable(value = ["categories"])
    override fun findAll(): List<BoardCategoryDto> {
        return boardCategoryMapper.toDtos(repository.findAll())
    }

    override fun deleteById(id: Long) {
        return repository.delete(findCategory(id))
    }

    private fun findCategory(id: Long): BoardCategory {
        return repository.findByIdOrNull(id) ?: throw NotFoundException("$id 에 해당하는 카테고리를 찾을 수 없습니다.")
    }
}