package com.microservice.property.repositories;

import com.microservice.property.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findByProperty_PropertyId(Long propertyId);
    List<Address> findByCity(String city);
}