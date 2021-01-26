package com.tamastudy.tama.mapper

import com.tamastudy.tama.dto.BoardCategoryDto.BoardCategoryInfo
import com.tamastudy.tama.entity.BoardCategory
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface BoardCategoryMapper : EntityMapper<BoardCategoryInfo, BoardCategory> {
    override fun toDto(entity: BoardCategory): BoardCategoryInfo
}