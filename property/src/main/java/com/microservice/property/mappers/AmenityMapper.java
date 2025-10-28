package com.microservice.property.mappers;

import com.microservice.property.dtos.AmenityDto;
import com.microservice.property.entities.Amenity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")

public interface AmenityMapper {
    AmenityMapper INSTANCE = Mappers.getMapper(AmenityMapper.class);
    Amenity toEntity(AmenityDto dto);
    AmenityDto toDto(Amenity entity);
}
