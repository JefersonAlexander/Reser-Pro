package com.microservice.property.mappers;

import com.microservice.property.dtos.PropertyDto;
import com.microservice.property.entities.Property;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")

public interface PropertyMapper {
    PropertyMapper INSTANCE = Mappers.getMapper(PropertyMapper.class);
    Property toEntity(PropertyDto dto);
    PropertyDto toDto(Property entity);
}
