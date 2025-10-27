package com.microservice.property.Dtos;

public record AddressDto(
        Long addressId,
        String country,
        String state,
        String city,
        String street,
        Float latitude,
        Float longitude
) {}
