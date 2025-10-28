package com.microservice.property.controllers;

import com.microservice.property.dtos.PublicationDto;
import com.microservice.property.services.PublicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/publications")
public class PublicationController {

    private final PublicationService publicationService;

    public PublicationController(PublicationService publicationService) {
        this.publicationService = publicationService;
    }

    /**
     * Endpoint para crear un nuevo tipo de publicación.
     * HTTP POST /api/v1/publications
     */
    @PostMapping
    public ResponseEntity<PublicationDto> createPublication(@RequestBody PublicationDto publicationDto) {
        PublicationDto newPublication = publicationService.createPublication(publicationDto);
        return new ResponseEntity<>(newPublication, HttpStatus.CREATED);
    }

    /**
     * Endpoint para obtener un tipo de publicación por su ID.
     * HTTP GET /api/v1/publications/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<PublicationDto> getPublicationById(@PathVariable Long id) {
        PublicationDto publicationDto = publicationService.getPublicationById(id);
        return ResponseEntity.ok(publicationDto);
    }

    /**
     * Endpoint para obtener un tipo de publicación por su nombre/tipo.
     * HTTP GET /api/v1/publications/type/{type}
     */
    @GetMapping("/type/{type}")
    public ResponseEntity<PublicationDto> getPublicationByType(@PathVariable String type) {
        PublicationDto publicationDto = publicationService.getPublicationByType(type);
        return ResponseEntity.ok(publicationDto);
    }

    /**
     * Endpoint para obtener todos los tipos de publicación.
     * HTTP GET /api/v1/publications
     */
    @GetMapping
    public ResponseEntity<List<PublicationDto>> getAllPublications() {
        List<PublicationDto> publications = publicationService.getAllPublications();
        return ResponseEntity.ok(publications);
    }

    /**
     * Endpoint para eliminar un tipo de publicación por su ID.
     * HTTP DELETE /api/v1/publications/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublication(@PathVariable Long id) {
        publicationService.deletePublication(id);
        return ResponseEntity.noContent().build();
    }
}
