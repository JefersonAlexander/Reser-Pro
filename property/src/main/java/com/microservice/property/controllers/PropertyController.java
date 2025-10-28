package com.microservice.property.controllers;

import com.microservice.property.dtos.PropertyDto;
import com.microservice.property.services.PropertyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*") // Permite peticiones desde cualquier origen
@RestController
@RequestMapping("/api/v1/properties")
public class PropertyController {

    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    /**
     * Endpoint para crear una nueva propiedad.
     * HTTP POST /api/v1/properties
     */
    @PostMapping
    public ResponseEntity<PropertyDto> createProperty(@RequestBody PropertyDto propertyDto) {
        PropertyDto newProperty = propertyService.createProperty(propertyDto);
        return new ResponseEntity<>(newProperty, HttpStatus.CREATED);
    }

    /**
     * Endpoint para obtener una propiedad por su ID.
     * HTTP GET /api/v1/properties/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<PropertyDto> getPropertyById(@PathVariable Long id) {
        PropertyDto propertyDto = propertyService.getPropertyById(id);
        return ResponseEntity.ok(propertyDto);
    }

    /**
     * Endpoint para obtener todas las propiedades.
     * HTTP GET /api/v1/properties
     */
    @GetMapping
    public ResponseEntity<List<PropertyDto>> getAllProperties() {
        List<PropertyDto> properties = propertyService.getAllProperties();
        return ResponseEntity.ok(properties);
    }

    /**
     * Endpoint para eliminar una propiedad por su ID.
     * HTTP DELETE /api/v1/properties/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProperty(@PathVariable Long id) {
        propertyService.deleteProperty(id);
        return ResponseEntity.noContent().build(); // HTTP 204 No Content
    }

    // --- Endpoints de consulta personalizados ---

    /**
     * Endpoint para obtener propiedades por el ID del propietario.
     * HTTP GET /api/v1/properties/owner/{ownerId}
     */
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<PropertyDto>> getPropertiesByOwner(@PathVariable Long ownerId) {
        List<PropertyDto> properties = propertyService.getPropertiesByOwner(ownerId);
        return ResponseEntity.ok(properties);
    }

    /**
     * Endpoint para obtener propiedades por ciudad.
     * HTTP GET /api/v1/properties/city/{city}
     */
    @GetMapping("/city/{city}")
    public ResponseEntity<List<PropertyDto>> getPropertiesByCity(@PathVariable String city) {
        List<PropertyDto> properties = propertyService.getPropertiesByCity(city);
        return ResponseEntity.ok(properties);
    }


    @PutMapping("/{id}")
    public ResponseEntity<PropertyDto> updateProperty(@PathVariable Long id, @RequestBody PropertyDto propertyDto) {
        PropertyDto updatedProperty = propertyService.updateProperty(id, propertyDto);
        return ResponseEntity.ok(updatedProperty);
    }
}