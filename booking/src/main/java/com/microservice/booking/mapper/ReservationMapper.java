package com.microservice.booking.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.microservice.booking.DTO.ReservationDTO;
import com.microservice.booking.entity.Reservation;




@Mapper(componentModel = "spring", uses = {
    ClientMapper.class, 
    ReservationStatusMapper.class
    })

public interface ReservationMapper {
    ReservationMapper INSTANCE = Mappers.getMapper(ReservationMapper.class);

    ReservationDTO toDto(Reservation reservation);
    ReservationDTO toDetailDTO(Reservation reservation);
    Reservation toEntity(ReservationDTO reservationDTO);
}