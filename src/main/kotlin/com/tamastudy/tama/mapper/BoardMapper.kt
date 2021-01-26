package com.tamastudy.tama.mapper

import com.tamastudy.tama.dto.board.BoardDto
import com.tamastudy.tama.dto.board.CreateBoardRequest
import com.tamastudy.tama.dto.board.UpdateBoardRequest
import com.tamastudy.tama.entity.Board
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
interface BoardMapper : EntityMapper<BoardDto, Board> {
    fun createRequestToEntity(createBoardRequest: CreateBoardRequest): Board
    fun updateRequestToEntity(updateBoardRequest: UpdateBoardRequest): Board
}