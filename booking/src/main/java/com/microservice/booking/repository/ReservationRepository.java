package com.microservice.booking.repository;

import com.microservice.booking.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByClientId(Long clientId);
    List<Reservation> findByStatus(String status);
    List<Reservation> findByDate(Date date);
}
