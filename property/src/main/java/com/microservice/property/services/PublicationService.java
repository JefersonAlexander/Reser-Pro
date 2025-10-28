package com.microservice.property.services;

import com.microservice.property.dtos.PublicationDto;
import com.microservice.property.entities.Publication;
import com.microservice.property.mappers.PublicationMapper;
import com.microservice.property.repositories.PublicationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublicationService {

    private final PublicationRepository publicationRepository;
    private final PublicationMapper publicationMapper;

    public PublicationService(PublicationRepository publicationRepository, PublicationMapper publicationMapper) {
        this.publicationRepository = publicationRepository;
        this.publicationMapper = publicationMapper;
    }

    @Transactional(readOnly = true)
    public List<PublicationDto> getAllPublications() {
        return publicationRepository.findAll()
                .stream()
                .map(publicationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PublicationDto getPublicationById(Long id) {
        return publicationRepository.findById(id)
                .map(publicationMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Publication not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public PublicationDto getPublicationByType(String type) {
        return publicationRepository.findByPublicationType(type)
                .map(publicationMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Publication not found with type: " + type));
    }

    @Transactional
    public PublicationDto createPublication(PublicationDto publicationDto) {
        Publication publication = publicationMapper.toEntity(publicationDto);
        Publication savedPublication = publicationRepository.save(publication);
        return publicationMapper.toDto(savedPublication);
    }

    @Transactional
    public void deletePublication(Long id) {
        if (!publicationRepository.existsById(id)) {
            throw new EntityNotFoundException("Publication not found with id: " + id);
        }
        publicationRepository.deleteById(id);
    }
}