package com.tamastudy.tama.mapper

import com.tamastudy.tama.dto.BoardDto.*
import com.tamastudy.tama.entity.Board
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
interface BoardMapper : EntityMapper<BoardInfo, Board> {
    @Mapping(target = "categoryId", source = "entity.category.id")
    override fun toDto(entity: Board): BoardInfo

    fun createRequestToEntity(createBoardRequest: BoardCreateRequest): Board
    fun updateRequestToEntity(updateBoardRequest: BoardUpdateRequest): Board
}