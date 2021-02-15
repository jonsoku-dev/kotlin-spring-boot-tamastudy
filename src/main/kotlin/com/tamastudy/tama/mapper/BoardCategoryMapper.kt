package com.tamastudy.tama.mapper

import com.tamastudy.tama.dto.BoardCategory.BoardCategoryDto
import com.tamastudy.tama.entity.BoardCategory
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy
import org.mapstruct.factory.Mappers

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface BoardCategoryMapper : EntityMapper<BoardCategoryDto, BoardCategory> {}