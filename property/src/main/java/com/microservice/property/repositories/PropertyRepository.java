package com.microservice.property.repositories;

import com.microservice.property.entities.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    List<Property> findByOwner_UserId(Long ownerId);
    List<Property> findByPropertyType(String propertyType);
    List<Property> findByPropertyStatus(String propertyStatus);
    List<Property> findByPublication_PublicationType(String publicationType);
    List<Property> findByAddress_City(String city);
    List<Property> findByAddress_State(String state);
    List<Property> findByAmenities_AmenityName(String amenityName);
}
