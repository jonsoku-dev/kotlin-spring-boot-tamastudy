package com.tamastudy.tama.mapper

import com.tamastudy.tama.dto.UserDto.CreateUserRequest
import com.tamastudy.tama.dto.UserDto.UserInfo
import com.tamastudy.tama.entity.User
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface UserMapper : EntityMapper<UserInfo, User> {
    // request 객체에 존재하지만 password 는 null 값이 된다.
//    @Mapping(target = "password", ignore = true)
    fun CreateUserRequestToEntity(createUserRequest: CreateUserRequest): User
}