package com.tamastudy.tama.mapper

import com.tamastudy.tama.dto.BoardCategory.*
import com.tamastudy.tama.entity.BoardCategory
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy
import org.mapstruct.factory.Mappers

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface BoardCategoryMapper : EntityMapper<BoardCategoryDto, BoardCategory> {
    companion object {
        val MAPPER: BoardCategoryMapper = Mappers.getMapper(BoardCategoryMapper::class.java)
    }
}