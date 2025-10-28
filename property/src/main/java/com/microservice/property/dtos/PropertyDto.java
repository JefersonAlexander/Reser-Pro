package com.microservice.property.dtos;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@JsonInclude(JsonInclude.Include.NON_NULL)

public class PropertyDetailsDto {
  private Long propertyId;
  private Long ownerId;
  private String propertyName;
  private String propertyDescription;
  private String propertyType;
  private String propertyStatus;
  private String reserveType;
  private Float reservePrice;
  private Integer maxCapacity;
  private Float salePrice;
  private AddressDto address;
  private List<PropertyPhotoDto> photos;
  private List<AmenityDto> amenities;
  private String publicationType;
}
