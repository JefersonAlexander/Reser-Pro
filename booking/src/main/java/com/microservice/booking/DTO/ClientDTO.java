package com.microservice.booking.DTO;
import lombok.AllArgsConstructor;
import lombok.Data; 
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ClientDTO {
    private Long id;
    private String name;
    private String username;
    private String phoneNumber;
    private String email;
}
