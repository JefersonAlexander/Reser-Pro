package com.microservice.property.mappers;

import com.microservice.property.dtos.AddressDto;
import com.microservice.property.entities.Address;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")

public interface AddressMapper {
    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);
    Address toEntity(AddressDto dto);
    AddressDto toDto(Address entity);
}
