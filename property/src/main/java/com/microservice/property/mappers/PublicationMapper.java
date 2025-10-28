package com.microservice.property.mappers;

import com.microservice.property.dtos.PublicationDto;
import com.microservice.property.entities.Publication;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")

public interface PublicationMapper {
    PublicationMapper INSTANCE = Mappers.getMapper(PublicationMapper.class);
    Publication toEntity(PublicationDto dto);
    PublicationDto toDto(Publication entity);
}
