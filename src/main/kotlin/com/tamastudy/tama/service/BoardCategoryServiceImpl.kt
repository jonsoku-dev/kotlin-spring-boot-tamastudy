package com.tamastudy.tama.service

import com.tamastudy.tama.dto.BoardCategory.BoardCategoryDto
import com.tamastudy.tama.dto.BoardCategory.BoardCategoryUpdateRequest
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
    override fun createCategory(category: BoardCategory): BoardCategoryDto {
        return BoardCategoryMapper.MAPPER.toDto(repository.save(category))
    }

    override fun updateCategory(id: Long, request: BoardCategoryUpdateRequest): BoardCategoryDto {
        val newCategory = findCategory(id).apply {
            this.name = request.name
        }

        return BoardCategoryMapper.MAPPER.toDto(repository.save(newCategory))
    }

    override fun findById(id: Long): BoardCategoryDto {
        return BoardCategoryMapper.MAPPER.toDto(findCategory(id))
    }

    override fun findAll(): List<BoardCategoryDto> {
        return BoardCategoryMapper.MAPPER.toDtos(repository.findAll())
    }

    override fun deleteById(id: Long) {
        val category = findCategory(id)
        return repository.delete(category)
    }

    private fun findCategory(id: Long): BoardCategory {
        return repository.findByIdOrNull(id) ?: throw NotFoundException("$id 에 해당하는 카테고리를 찾을 수 없습니다.")
    }
}