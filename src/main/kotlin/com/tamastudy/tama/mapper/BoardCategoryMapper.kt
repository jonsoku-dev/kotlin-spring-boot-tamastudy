package com.tamastudy.tama.mapper

import com.tamastudy.tama.dto.BoardCategoryDto.*
import com.tamastudy.tama.entity.Board
import com.tamastudy.tama.entity.BoardCategory
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy
import org.mapstruct.factory.Mappers

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface BoardCategoryMapper : EntityMapper<BoardCategoryInfo, BoardCategory> {

    companion object {
        val MAPPER: BoardCategoryMapper = Mappers.getMapper(BoardCategoryMapper::class.java)
    }

    fun createRequestToEntity(boardCategoryCreateRequest: BoardCategoryCreateRequest): BoardCategory
    fun updateRequestToEntity(boardCategoryUpdateRequest: BoardCategoryUpdateRequest): BoardCategory
}