package com.tamastudy.tama.mapper

import com.tamastudy.tama.dto.BoardDto.*
import com.tamastudy.tama.entity.Board
import com.tamastudy.tama.service.BoardService
import com.tamastudy.tama.service.UserService
import org.mapstruct.*
import org.mapstruct.factory.Mappers


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = [UserMapper::class])
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
            Mapping(target = "email", source = "entity.user.email")
    )
    fun toDto(entity: Board): BoardInfo

    fun createRequestToEntity(createBoardRequest: BoardCreateRequest): Board
    fun updateRequestToEntity(updateBoardRequest: BoardUpdateRequest): Board
}