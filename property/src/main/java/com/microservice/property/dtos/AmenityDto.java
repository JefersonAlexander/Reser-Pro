package com.microservice.property.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@JsonInclude(JsonInclude.Include.NON_NULL)

public class AmenityDto {
    private Long amenityId;
    private String amenityName;
    private Integer quantity;
}
