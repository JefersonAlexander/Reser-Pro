package com.microservice.property.Dtos;

public record PropertyDetailsDto(
        Long propertyId,
        Long ownerId,
        String propertyName,
        String propertyDescription,
        String propertyType,
        String propertyStatus,
        String reserveType,
        Float reservePrice,
        Integer maxCapacity,
        Float salePrice,

        // Objeto anidado desde la tabla 'address'
        AddressDto address,

        List<PropertyPhotoDto> photos,

        List<AmenityDto> amenities,

        String publicationType
) {}
