package com.reser_pro.auth_service.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.reser_pro.auth_service.DTO.UserDTO;
import com.reser_pro.auth_service.entity.Role;
import com.reser_pro.auth_service.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "roles", source = "roles")
    UserDTO toDTO(User user);

    

    default String mapRole(Role role) {
        return role.getRoleName();
    }
}
