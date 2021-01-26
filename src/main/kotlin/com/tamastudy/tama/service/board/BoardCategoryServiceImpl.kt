package com.tamastudy.tama.service.board

import com.tamastudy.tama.dto.board.BoardCategoryDto
import com.tamastudy.tama.dto.board.BoardDto
import com.tamastudy.tama.dto.board.convertBoardCategoryDto
import com.tamastudy.tama.dto.board.convertBoardDto
import com.tamastudy.tama.entity.Board
import com.tamastudy.tama.entity.BoardCategory
import com.tamastudy.tama.repository.board.BoardCategoryRepository
import javassist.NotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class BoardCategoryServiceImpl(
        private val repository: BoardCategoryRepository
) : BoardCategoryService {
    override fun createCategory(category: BoardCategory): BoardCategoryDto {
        return BoardCategoryDto().convertBoardCategoryDto(repository.save(category))
    }

    override fun updateCategory(category: BoardCategory): BoardCategoryDto {
        val newCategory = category.id?.let {
            findCategory(it).let {
                repository.save(category)
            }
        } ?: throw NotFoundException("${category.id} 에 해당하는 카테고리를 찾을 수 없습니다.")

        return BoardCategoryDto().convertBoardCategoryDto(newCategory)
    }

    override fun findById(id: Long): BoardCategoryDto {
        return BoardCategoryDto().convertBoardCategoryDto(findCategory(id))
    }

    private fun findCategory(id: Long): BoardCategory {
        return repository.findByIdOrNull(id) ?: throw NotFoundException("$id 에 해당하는 카테고리를 찾을 수 없습니다.")
    }
}