package com.microservice.property.mappers;

import com.microservice.property.dtos.PropertyPhotoDto;
import com.microservice.property.entities.PropertyPhoto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")

public interface PropertyPhotoMapper {
    PropertyPhotoMapper INSTANCE = Mappers.getMapper(PropertyPhotoMapper.class);
    PropertyPhoto toEntity(PropertyPhotoDto dto);
    PropertyPhotoDto toDto(PropertyPhoto entity);
}
