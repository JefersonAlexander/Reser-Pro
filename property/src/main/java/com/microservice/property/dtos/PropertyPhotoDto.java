package com.microservice.property.Dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)

public class PropertyPhotoDto {
    private Long photoId;
    private String photoUrl;
    private String photoName;
}
