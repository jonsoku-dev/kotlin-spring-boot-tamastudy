package com.tamastudy.tama.mapper

import com.tamastudy.tama.dto.BoardDto.*
import com.tamastudy.tama.entity.Board
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
interface BoardMapper : EntityMapper<BoardInfo, Board> {
    fun createRequestToEntity(createBoardRequest: BoardCreateRequest): Board
    fun updateRequestToEntity(updateBoardRequest: BoardUpdateRequest): Board
}