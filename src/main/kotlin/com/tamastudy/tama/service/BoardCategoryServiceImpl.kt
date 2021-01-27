package com.tamastudy.tama.service

import com.tamastudy.tama.dto.BoardCategoryDto.BoardCategoryInfo
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
    override fun createCategory(category: BoardCategory): BoardCategoryInfo {
        return BoardCategoryMapper.MAPPER.toDto(repository.save(category))
    }

    override fun updateCategory(id: Long, category: BoardCategory): BoardCategoryInfo {
        val newCategory = findCategory(id).let {
            it.name = category.name
            repository.save(it)
        }

        return BoardCategoryMapper.MAPPER.toDto(newCategory)
    }

    override fun findById(id: Long): BoardCategoryInfo {
        return BoardCategoryMapper.MAPPER.toDto(findCategory(id))
    }

    override fun findAll(): List<BoardCategoryInfo> {
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