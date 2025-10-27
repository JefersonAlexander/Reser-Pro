package com.microservice.property.Dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)

public class PublicationDto {
    private Long publicationId;
    private String publicationType;
}
