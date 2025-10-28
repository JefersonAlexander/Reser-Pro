package com.microservice.booking.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.microservice.booking.DTO.ReservationStatusDTO;
import com.microservice.booking.entity.ReservationStatus;

@Mapper(componentModel = "spring")

public interface ReservationStatusMapper {
    ReservationStatusMapper INSTANCE = Mappers.getMapper(ReservationStatusMapper.class);
    ReservationStatusDTO toDto(ReservationStatus reservationStatus);
    ReservationStatus toEntity(ReservationStatusDTO reservationStatus);
}

