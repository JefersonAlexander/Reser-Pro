package com.microservice.property.controllers;

import com.microservice.property.dtos.AmenityDto;
import com.microservice.property.services.AmenityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/amenities")
public class AmenityController {

    private final AmenityService amenityService;

    public AmenityController(AmenityService amenityService) {
        this.amenityService = amenityService;
    }

    /**
     * Endpoint para crear un nuevo amenity.
     * HTTP POST /api/v1/amenities
     */
    @PostMapping
    public ResponseEntity<AmenityDto> createAmenity(@RequestBody AmenityDto amenityDto) {
        AmenityDto newAmenity = amenityService.createAmenity(amenityDto);
        return new ResponseEntity<>(newAmenity, HttpStatus.CREATED);
    }

    /**
     * Endpoint para obtener un amenity por su ID.
     * HTTP GET /api/v1/amenities/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<AmenityDto> getAmenityById(@PathVariable Long id) {
        AmenityDto amenityDto = amenityService.getAmenityById(id);
        return ResponseEntity.ok(amenityDto);
    }

    /**
     * Endpoint para obtener un amenity por su nombre.
     * HTTP GET /api/v1/amenities/name/{name}
     */
    @GetMapping("/name/{name}")
    public ResponseEntity<AmenityDto> getAmenityByName(@PathVariable String name) {
        // Es necesario codificar el nombre en la URL si contiene espacios
        AmenityDto amenityDto = amenityService.getAmenityByName(name);
        return ResponseEntity.ok(amenityDto);
    }

    /**
     * Endpoint para obtener todos los amenities.
     * HTTP GET /api/v1/amenities
     */
    @GetMapping
    public ResponseEntity<List<AmenityDto>> getAllAmenities() {
        List<AmenityDto> amenities = amenityService.getAllAmenities();
        return ResponseEntity.ok(amenities);
    }

    /**
     * Endpoint para actualizar un amenity.
     * HTTP PUT /api/v1/amenities/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<AmenityDto> updateAmenity(@PathVariable Long id, @RequestBody AmenityDto amenityDto) {
        AmenityDto updatedAmenity = amenityService.updateAmenity(id, amenityDto);
        return ResponseEntity.ok(updatedAmenity);
    }

    /**
     * Endpoint para eliminar un amenity por su ID.
     * HTTP DELETE /api/v1/amenities/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAmenity(@PathVariable Long id) {
        amenityService.deleteAmenity(id);
        return ResponseEntity.noContent().build();
    }
}
