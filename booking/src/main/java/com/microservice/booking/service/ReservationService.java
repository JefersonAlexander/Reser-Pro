package com.microservice.booking.service;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import com.microservice.booking.repository.ReservationRepository;
import com.microservice.booking.mapper.ReservationMapper;
import com.microservice.booking.DTO.ReservationDTO;
import com.microservice.booking.DTO.ReservationDetailDTO;
import com.microservice.booking.DTO.UpdateReservationStatusDTO;
import com.microservice.booking.entity.Reservation;

import com.microservice.booking.entity.Client;
import com.microservice.booking.repository.ClientRepository;
import com.microservice.booking.entity.ReservationStatus;
import com.microservice.booking.repository.ReservationStatusRepository;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;


@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ClientRepository clientRepository;
    private final ReservationStatusRepository reservationStatusRepository;
    private final ReservationMapper reservationMapper;

    public ReservationService(ReservationRepository reservationRepository,
                              ClientRepository clientRepository,
                              ReservationStatusRepository reservationStatusRepository,
                              ReservationMapper reservationMapper) {
        this.reservationRepository = reservationRepository;
        this.clientRepository = clientRepository;
        this.reservationStatusRepository = reservationStatusRepository;
        this.reservationMapper = reservationMapper;
    }

    @Cacheable(value = "reservationsList")
    public List<ReservationDTO> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(reservationMapper::toDto)
                .toList();
    }

    @Cacheable(value = "reservations", key = "#id")
    public ReservationDetailDTO getReservationById(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found with id: " + id));
        return reservationMapper.toDetailDTO(reservation);
    }

    @CachePut(value = "reservations", key = "#result.id")
    @CacheEvict(value = "reservationsList", allEntries = true)
    public ReservationDetailDTO createReservation(ReservationDTO reservationDTO) {

        Client client = clientRepository.findById(reservationDTO.getClientId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "User not found with id: " + reservationDTO.getClientId()));

        ReservationStatus status = reservationStatusRepository.findById(reservationDTO.getStatusId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Status not found with id: " + reservationDTO.getStatusId()));

        Reservation reservation = reservationMapper.toEntity(reservationDTO);
        reservation.setClient(client);
        reservation.setStatus(status);

        reservation = reservationRepository.save(reservation);

        return reservationMapper.toDetailDTO(reservation);
    }
    @Caching(evict = {
            @CacheEvict(value = "reservations", key = "#id"),
            @CacheEvict(value = "reservationsList", allEntries = true)
    })
    
    public ReservationDetailDTO updateStatusReservation(Long id, UpdateReservationStatusDTO dto) {
    Reservation existingReservation = reservationRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Reservation not found with id: " + id));

    ReservationStatus status = reservationStatusRepository.findById(dto.getStatusId())
            .orElseThrow(() -> new EntityNotFoundException(
                    "Status not found with id: " + dto.getStatusId()));

    existingReservation.setStatus(status);
    existingReservation = reservationRepository.save(existingReservation);

    return reservationMapper.toDetailDTO(existingReservation);
    }
}
    
