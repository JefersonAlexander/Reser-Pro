package com.microservice.property.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@JsonInclude(JsonInclude.Include.NON_NULL)

public class AddressDto {
  private Long addressId;
  private String country;
  private String state;
  private String city;
  private String street;
  private Float latitude;
  private Float longitud;
}
