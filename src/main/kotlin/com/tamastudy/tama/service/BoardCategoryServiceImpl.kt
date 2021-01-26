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
        private val boardCategoryMapper: BoardCategoryMapper,
        private val repository: BoardCategoryRepository
) : BoardCategoryService {
    override fun createCategory(category: BoardCategory): BoardCategoryInfo {
        return boardCategoryMapper.toDto(repository.save(category))
    }

    override fun updateCategory(category: BoardCategory): BoardCategoryInfo {
        val newCategory = category.id?.let {
            findCategory(it).let {
                repository.save(category)
            }
        } ?: throw NotFoundException("${category.id} 에 해당하는 카테고리를 찾을 수 없습니다.")

        return boardCategoryMapper.toDto(newCategory)
    }

    override fun findById(id: Long): BoardCategoryInfo {
        return boardCategoryMapper.toDto(findCategory(id))
    }

    private fun findCategory(id: Long): BoardCategory {
        return repository.findByIdOrNull(id) ?: throw NotFoundException("$id 에 해당하는 카테고리를 찾을 수 없습니다.")
    }
}