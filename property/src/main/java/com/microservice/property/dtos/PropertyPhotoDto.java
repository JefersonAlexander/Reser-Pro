package com.microservice.property.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)

public class PropertyPhotoDto {
    private Long photoId;
    private String photoUrl;
    private String photoName;
}
