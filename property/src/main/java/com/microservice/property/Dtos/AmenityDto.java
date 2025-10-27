package com.microservice.property.Dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)

public class AmenityDto {
    private Long amenityId;
    private String amenityName;
    private Integer quantity;
}
