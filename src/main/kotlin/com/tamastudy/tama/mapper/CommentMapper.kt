package com.tamastudy.tama.mapper

import com.tamastudy.tama.dto.Comment.CommentDto
import com.tamastudy.tama.dto.Comment.CommentFlatDto
import com.tamastudy.tama.entity.Comment
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.ReportingPolicy


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = [UserMapper::class])
interface CommentMapper : EntityMapper<CommentDto, Comment> {

    @Mappings(
            Mapping(target = "user", source = "dto.user"),
            Mapping(target = "board", source = "dto.board"),
            Mapping(target = "superComment", source = "dto.superComment"),
    )
    override fun toEntity(dto: CommentDto): Comment

    @Mappings(
            Mapping(target = "user", source = "entity.user"),
            Mapping(target = "board", source = "entity.board"),
            Mapping(target = "superComment", source = "entity.superComment"),
    )
    override fun toDto(entity: Comment): CommentDto

    @Mappings(
            Mapping(target = "commentId", source = "entity.id"),
            Mapping(target = "userId", source = "entity.user.id"),
            Mapping(target = "username", source = "entity.user.username"),
//            Mapping(target = "superComment", source = "entity.superComment"),
            Mapping(target = "subComment", source = "entity.subComment"),
    )
    fun toFlatDto(entity: Comment): CommentFlatDto

    fun toFlatDtos(entities: List<Comment>): List<CommentFlatDto>
}