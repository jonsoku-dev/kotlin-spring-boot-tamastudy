package com.tamastudy.tama.mapper

import com.tamastudy.tama.dto.BoardDto
import com.tamastudy.tama.dto.BoardFlatDto
import com.tamastudy.tama.entity.Board
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.ReportingPolicy


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface BoardMapper : EntityMapper<BoardDto, Board> {

    @Mappings(
            Mapping(target = "user", source = "dto.user"),
            Mapping(target = "category", source = "dto.category"),
//            Mapping(target = "comments", source = "dto.comments"),
    )
    override fun toEntity(dto: BoardDto): Board

    @Mappings(
            Mapping(target = "user", source = "entity.user"),
            Mapping(target = "category", source = "entity.category"),
//            Mapping(target = "comments", source = "entity.comments"),
    )
    override fun toDto(entity: Board): BoardDto


    @Mappings(
            Mapping(target = "boardId", source = "entity.id"),
            Mapping(target = "userId", source = "entity.user.id"),
            Mapping(target = "username", source = "entity.user.username"),
            Mapping(target = "email", source = "entity.user.email"),
            Mapping(target = "categoryId", source = "entity.category.id"),
            Mapping(target = "categoryName", source = "entity.category.name"),
//            Mapping(target = "comments", source = "comments")
    )
    fun toFlatDto(entity: Board): BoardFlatDto
}