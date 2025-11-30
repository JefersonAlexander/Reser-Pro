package com.microservice.booking.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ReservationDetailDTO {
    private Long id;
    private ClientDTO client;
    private Long roomId;
    private String startDate;
    private String endDate;
    private Double totalPrice;
    private ReservationStatusDTO status;
    private Integer numberOfGuests;
}

