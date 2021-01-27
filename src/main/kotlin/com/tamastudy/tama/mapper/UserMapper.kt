package com.tamastudy.tama.mapper

import com.tamastudy.tama.dto.UserDto.CreateUserRequest
import com.tamastudy.tama.dto.UserDto.UserInfo
import com.tamastudy.tama.entity.User
import org.mapstruct.Mapper
import org.mapstruct.MappingTarget
import org.mapstruct.ReportingPolicy
import org.mapstruct.factory.Mappers

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface UserMapper {

    companion object {
        val MAPPER: UserMapper = Mappers.getMapper(UserMapper::class.java)
    }

    fun toEntity(dto: UserInfo): User

    fun toEntities(dtos: List<UserInfo>): List<User>

    fun toDto(entity: User): UserInfo

    fun toDtos(entities: List<User>): List<UserInfo>

    fun CreateUserRequestToEntity(createUserRequest: CreateUserRequest): User
}