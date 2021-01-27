package com.tamastudy.tama.mapper

import com.tamastudy.tama.dto.BoardDto.*
import com.tamastudy.tama.entity.Board
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.ReportingPolicy
import org.mapstruct.factory.Mappers


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface BoardMapper {

    companion object {
        val MAPPER: BoardMapper = Mappers.getMapper(BoardMapper::class.java)
    }

    @Mapping(target = "user.id", source = "userId")
    fun toEntity(dto: BoardInfo): Board

    @Mappings(
            Mapping(target = "categoryId", source = "entity.category.id"),
            Mapping(target = "userId", source = "entity.user.id"),
            Mapping(target = "username", source = "entity.user.username"),
            Mapping(target = "email", source = "entity.user.email"),
    )
    fun toDto(entity: Board): BoardInfo

    fun createRequestToEntity(createBoardRequest: BoardCreateRequest): Board
    fun updateRequestToEntity(updateBoardRequest: BoardUpdateRequest): Board
}