package com.microservice.booking.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;  
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.microservice.booking.DTO.ReservationDTO;
import com.microservice.booking.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import com.microservice.booking.DTO.ReservationDetailDTO;

@Tag(name = "Reservas", description = "Endpoints para gestionar reservas")
@RestController
@RequestMapping("/api/reservations")

public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }


    @GetMapping
    public ResponseEntity<List<ReservationDTO>> getAllReservations() {
        List<ReservationDTO> reservations = reservationService.getAllReservations();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> getReservationById(@PathVariable Long id) {
        ReservationDTO reservation = reservationService.getReservationById(id);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }  
    @PostMapping
    public ResponseEntity<ReservationDetailDTO> createReservation(@RequestBody ReservationDTO reservationDTO) {
        ReservationDetailDTO createdReservation = reservationService.createReservation(reservationDTO);
        return new ResponseEntity<>(createdReservation, HttpStatus.CREATED);
    }
    
}
    

