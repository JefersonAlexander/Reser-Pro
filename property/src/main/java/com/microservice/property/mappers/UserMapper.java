package com.microservice.property.mappers;

import com.microservice.property.dtos.UserDto;
import com.microservice.property.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")

public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    User toEntity(UserDto dto);
    UserDto toDto(User entity);
}
