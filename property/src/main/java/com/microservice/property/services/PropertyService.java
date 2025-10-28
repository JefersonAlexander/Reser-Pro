package com.microservice.property.services;

import com.microservice.property.dtos.AmenityDto;
import com.microservice.property.dtos.PropertyDto;
import com.microservice.property.entities.*;
import com.microservice.property.mappers.AddressMapper;
import com.microservice.property.mappers.AmenityMapper;
import com.microservice.property.mappers.PropertyMapper;
import com.microservice.property.mappers.PropertyPhotoMapper;
import com.microservice.property.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final PublicationRepository publicationRepository;
    private final AmenityRepository amenityRepository;
    private final PropertyMapper propertyMapper;
    private final AddressMapper addressMapper;
    private final AmenityMapper amenityMapper;
    private final PropertyPhotoMapper photoMapper;

    public PropertyService(PropertyRepository propertyRepository,
                           UserRepository userRepository,
                           PublicationRepository publicationRepository,
                           AmenityRepository amenityRepository,
                           PropertyMapper propertyMapper,
                           AddressMapper addressMapper,
                           AmenityMapper amenityMapper,
                           PropertyPhotoMapper photoMapper) {
        this.propertyRepository = propertyRepository;
        this.userRepository = userRepository;
        this.publicationRepository = publicationRepository;
        this.amenityRepository = amenityRepository;
        this.propertyMapper = propertyMapper;
        this.addressMapper = addressMapper;
        this.amenityMapper = amenityMapper;
        this.photoMapper = photoMapper;
    }

    @Transactional(readOnly = true)
    public List<PropertyDto> getAllProperties() {
        return propertyRepository.findAll()
                .stream()
                .map(this::mapPropertyToDto) // Usamos un método helper
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PropertyDto getPropertyById(Long id) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Property not found with id: " + id));
        return mapPropertyToDto(property);
    }

    @Transactional
    public PropertyDto createProperty(PropertyDto propertyDto) {

        Property property = propertyMapper.toEntity(propertyDto);
        User owner = userRepository.findById(propertyDto.getOwnerId())
                .orElseThrow(() -> new EntityNotFoundException("Owner (User) not found with id: " + propertyDto.getOwnerId()));
        property.setOwner(owner);

        Publication publication = publicationRepository.findByPublicationType(propertyDto.getPublicationType())
                .orElseGet(() -> {
                    Publication newPub = new Publication();
                    newPub.setPublicationType(propertyDto.getPublicationType());
                    return publicationRepository.save(newPub);
                });
        property.setPublication(publication);

        if (propertyDto.getAddress() != null) {
            Address address = addressMapper.toEntity(propertyDto.getAddress());
            address.setProperty(property); // Establecer la relación bidireccional
            property.setAddress(address);
        }

        if (propertyDto.getPhotos() != null) {
            Set<PropertyPhoto> photos = propertyDto.getPhotos().stream()
                    .map(photoDto -> {
                        PropertyPhoto photo = photoMapper.toEntity(photoDto);
                        photo.setProperty(property);
                        return photo;
                    })
                    .collect(Collectors.toSet());
            property.setPhotos(photos);
        }


        if (propertyDto.getAmenities() != null) {
            // Asumimos que los amenities ya existen y solo necesitamos las referencias
            Set<Long> amenityIds = propertyDto.getAmenities().stream()
                    .map(AmenityDto::getAmenityId)
                    .collect(Collectors.toSet());

            Set<Amenity> amenities = amenityRepository.findAllById(amenityIds)
                    .stream().collect(Collectors.toSet());
            property.setAmenities(amenities);
        }
        Property savedProperty = propertyRepository.save(property);
        return mapPropertyToDto(savedProperty);
    }

    @Transactional
    public void deleteProperty(Long id) {
        if (!propertyRepository.existsById(id)) {
            throw new EntityNotFoundException("Property not found with id: " + id);
        }
        propertyRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<PropertyDto> getPropertiesByOwner(Long ownerId) {
        return propertyRepository.findByOwner_UserId(ownerId)
                .stream()
                .map(this::mapPropertyToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PropertyDto> getPropertiesByCity(String city) {
        return propertyRepository.findByAddress_City(city)
                .stream()
                .map(this::mapPropertyToDto)
                .collect(Collectors.toList());
    }

    /**
     * Método helper para mapear manualmente las partes que MapStruct simple no puede.
     * Esto es necesario porque el DTO y la Entidad no coinciden 1-a-1
     * (ej. DTO tiene ownerId, Entidad tiene objeto User).
     */
    private PropertyDto mapPropertyToDto(Property property) {
        PropertyDto dto = propertyMapper.toDto(property);

        // Mapeo manual de campos anidados
        if (property.getOwner() != null) {
            dto.setOwnerId(property.getOwner().getUserId());
        }
        if (property.getPublication() != null) {
            dto.setPublicationType(property.getPublication().getPublicationType());
        }
        if (property.getAddress() != null) {
            dto.setAddress(addressMapper.toDto(property.getAddress()));
        }
        if (property.getPhotos() != null) {
            dto.setPhotos(property.getPhotos().stream()
                    .map(photoMapper::toDto)
                    .collect(Collectors.toList()));
        }
        if (property.getAmenities() != null) {
            dto.setAmenities(property.getAmenities().stream()
                    .map(amenityMapper::toDto)
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    @Transactional
    public PropertyDto updateProperty(Long id, PropertyDto propertyDto) {
        // 1. Buscar la propiedad existente
        Property existingProperty = propertyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Property not found with id: " + id));

        // 2. Actualizar campos simples
        existingProperty.setPropertyName(propertyDto.getPropertyName());
        existingProperty.setPropertyDescription(propertyDto.getPropertyDescription());
        existingProperty.setPropertyType(propertyDto.getPropertyType());
        existingProperty.setPropertyStatus(propertyDto.getPropertyStatus());
        existingProperty.setReserveType(propertyDto.getReserveType());
        existingProperty.setReservePrice(propertyDto.getReservePrice());
        existingProperty.setMaxCapacity(propertyDto.getMaxCapacity());
        existingProperty.setSalePrice(propertyDto.getSalePrice());

        // 3. Actualizar relaciones

        // Relación @ManyToOne: Owner (User)
        if (propertyDto.getOwnerId() != null && !propertyDto.getOwnerId().equals(existingProperty.getOwner().getUserId())) {
            User newOwner = userRepository.findById(propertyDto.getOwnerId())
                    .orElseThrow(() -> new EntityNotFoundException("Owner (User) not found with id: " + propertyDto.getOwnerId()));
            existingProperty.setOwner(newOwner);
        }

        // Relación @ManyToOne: Publication
        if (propertyDto.getPublicationType() != null && !propertyDto.getPublicationType().equals(existingProperty.getPublication().getPublicationType())) {
            Publication newPublication = publicationRepository.findByPublicationType(propertyDto.getPublicationType())
                    .orElseThrow(() -> new EntityNotFoundException("PublicationType not found: " + propertyDto.getPublicationType()));
            existingProperty.setPublication(newPublication);
        }

        // Relación @OneToOne: Address
        if (propertyDto.getAddress() != null) {
            Address existingAddress = existingProperty.getAddress();
            if (existingAddress == null) {
                existingAddress = new Address();
                existingAddress.setProperty(existingProperty);
                existingProperty.setAddress(existingAddress);
            }
            // Actualizar campos de la dirección
            existingAddress.setCountry(propertyDto.getAddress().getCountry());
            existingAddress.setState(propertyDto.getAddress().getState());
            existingAddress.setCity(propertyDto.getAddress().getCity());
            existingAddress.setStreet(propertyDto.getAddress().getStreet());
            existingAddress.setLatitude(propertyDto.getAddress().getLatitude());
            existingAddress.setLongitud(propertyDto.getAddress().getLongitud());
        }

        // Relación @OneToMany: Photos (con orphanRemoval=true)
        if (propertyDto.getPhotos() != null) {
            // Limpiamos las fotos antiguas (JPA las borrará gracias a orphanRemoval)
            existingProperty.getPhotos().clear();

            // Añadimos las nuevas fotos
            Set<PropertyPhoto> newPhotos = propertyDto.getPhotos().stream()
                    .map(photoDto -> {
                        PropertyPhoto photo = photoMapper.toEntity(photoDto);
                        photo.setProperty(existingProperty); // Establecer relación
                        return photo;
                    })
                    .collect(Collectors.toSet());
            existingProperty.getPhotos().addAll(newPhotos);
        }

        // Relación @ManyToMany: Amenities
        if (propertyDto.getAmenities() != null) {
            Set<Long> amenityIds = propertyDto.getAmenities().stream()
                    .map(AmenityDto::getAmenityId)
                    .collect(Collectors.toSet());

            Set<Amenity> newAmenities = amenityRepository.findAllById(amenityIds)
                    .stream().collect(Collectors.toSet());

            // Asignamos el nuevo conjunto de amenities
            existingProperty.setAmenities(newAmenities);
        }

        // 4. Guardar la entidad actualizada
        Property updatedProperty = propertyRepository.save(existingProperty);

        // 5. Devolver el DTO
        return mapPropertyToDto(updatedProperty);
    }
}
