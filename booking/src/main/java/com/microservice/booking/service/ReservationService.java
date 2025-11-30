package com.microservice.booking.service;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.microservice.booking.repository.ReservationRepository;
import com.microservice.booking.mapper.ReservationMapper;
import com.microservice.booking.DTO.ReservationDTO;
import com.microservice.booking.DTO.ReservationDetailDTO;
import com.microservice.booking.entity.Reservation;

import com.microservice.booking.entity.Client;
import com.microservice.booking.repository.ClientRepository;
import com.microservice.booking.entity.ReservationStatus;
import com.microservice.booking.repository.ReservationStatusRepository;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;




@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;
    private ClientRepository clientRepository;
    private ReservationStatusRepository reservationStatusRepository;
    private ReservationMapper reservationMapper;

    public ReservationService(ReservationRepository reservationRepository,
                              ClientRepository clientRepository,
                              ReservationStatusRepository reservationStatusRepository,
                              ReservationMapper reservationMapper) {
        this.reservationRepository = reservationRepository;
        this.clientRepository = clientRepository;
        this.reservationStatusRepository = reservationStatusRepository;
        this.reservationMapper = reservationMapper;
    }

    public List<ReservationDTO> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(reservationMapper::toDto)
                .toList();
    }

    public ReservationDTO getReservationById(Long id) {
        Reservation reservation = reservationRepository.findById(id).orElse(null);
        return reservationMapper.toDto(reservation);
    }

    public ReservationDetailDTO createReservation(ReservationDTO reservationDTO) {
        
      
        Client client = clientRepository.findById(reservationDTO.getClientId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + reservationDTO.getClientId()));

        
        ReservationStatus status = reservationStatusRepository.findById(reservationDTO.getStatusId())
                .orElseThrow(() -> new EntityNotFoundException("Status not found with id: " + reservationDTO.getStatusId()));
        

        Reservation reservation = reservationMapper.toEntity(reservationDTO);
        reservation.setClient(client);
        reservation.setStatus(status);
        reservation = reservationRepository.save(reservation);

        return reservationMapper.toDetailDTO(reservation);
    }

}
    

