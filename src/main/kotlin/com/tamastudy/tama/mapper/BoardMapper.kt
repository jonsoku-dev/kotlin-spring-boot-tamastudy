package com.tamastudy.tama.mapper

import com.tamastudy.tama.dto.Board.*
import com.tamastudy.tama.entity.Board
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.ReportingPolicy
import org.mapstruct.factory.Mappers


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface BoardMapper : EntityMapper<BoardDto, Board> {

    companion object {
        val MAPPER: BoardMapper = Mappers.getMapper(BoardMapper::class.java)
    }

    @Mappings(
            Mapping(target = "user", source = "dto.user"),
            Mapping(target = "category", source = "dto.category"),
    )
    override fun toEntity(dto: BoardDto): Board

    @Mappings(
            Mapping(target = "user", source = "entity.user"),
            Mapping(target = "category", source = "entity.category"),
    )
    override fun toDto(entity: Board): BoardDto
}