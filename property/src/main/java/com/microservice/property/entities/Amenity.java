package com.microservice.property.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Entity
@Table(name = "amenities")
@Data
@Getter
@Setter

public class Amenity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "amenity_id")
    private Long amenityId;

    @Column(name = "amenity_name")
    private String amenityName;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToMany(mappedBy = "amenities")
    private Set<Property> properties;

    @JsonCreator
    public Amenity(
            @JsonProperty("amenity_id") Long amenityId,
            @JsonProperty("amenity_name") String amenityName,
            @JsonProperty("quantity") Integer quantity,
            @JsonProperty("properties") Set<Property> properties)
    {
        this.amenityId = amenityId;
        this.amenityName = amenityName;
        this.quantity = quantity;
        this.properties = properties;
    }

    public Amenity() { }
}
