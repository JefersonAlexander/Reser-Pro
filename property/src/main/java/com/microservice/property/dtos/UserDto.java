package com.microservice.property.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@JsonInclude(JsonInclude.Include.NON_NULL)

public class UserDto {
 private Long userId;
 private String username;
 private String email;
}
