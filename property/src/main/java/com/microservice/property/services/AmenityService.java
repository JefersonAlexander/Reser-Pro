package com.microservice.property.services;

import com.microservice.property.dtos.AmenityDto;
import com.microservice.property.entities.Amenity;
import com.microservice.property.mappers.AmenityMapper;
import com.microservice.property.repositories.AmenityRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AmenityService {

    private final AmenityRepository amenityRepository;
    private final AmenityMapper amenityMapper;

    public AmenityService(AmenityRepository amenityRepository, AmenityMapper amenityMapper) {
        this.amenityRepository = amenityRepository;
        this.amenityMapper = amenityMapper;
    }

    @Transactional(readOnly = true)
    public List<AmenityDto> getAllAmenities() {
        return amenityRepository.findAll()
                .stream()
                .map(amenityMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AmenityDto getAmenityById(Long id) {
        return amenityRepository.findById(id)
                .map(amenityMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Amenity not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public AmenityDto getAmenityByName(String name) {
        return amenityRepository.findByAmenityName(name)
                .map(amenityMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Amenity not found with name: " + name));
    }

    @Transactional
    public AmenityDto createAmenity(AmenityDto amenityDto) {
        Amenity amenity = amenityMapper.toEntity(amenityDto);
        Amenity savedAmenity = amenityRepository.save(amenity);
        return amenityMapper.toDto(savedAmenity);
    }

    @Transactional
    public AmenityDto updateAmenity(Long id, AmenityDto amenityDto) {
        Amenity existingAmenity = amenityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Amenity not found with id: " + id));

        existingAmenity.setAmenityName(amenityDto.getAmenityName());
        existingAmenity.setQuantity(amenityDto.getQuantity());

        Amenity updatedAmenity = amenityRepository.save(existingAmenity);
        return amenityMapper.toDto(updatedAmenity);
    }

    @Transactional
    public void deleteAmenity(Long id) {
        if (!amenityRepository.existsById(id)) {
            throw new EntityNotFoundException("Amenity not found with id: " + id);
        }
        amenityRepository.deleteById(id);
    }
}