package com.microservice.booking.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.microservice.booking.DTO.ClientDTO;
import com.microservice.booking.entity.Client;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    ClientDTO toDto(Client client);
    Client toEntity(ClientDTO clientDTO);
}