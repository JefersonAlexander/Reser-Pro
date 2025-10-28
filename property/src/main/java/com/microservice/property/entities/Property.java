package com.microservice.property.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Entity
@Table(name = "properties")
@Data
@Getter
@Setter

public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "property_id")
    private Long propertyId;

    @Column(name = "property_name")
    private String propertyName;

    @Column(name = "property_description", length = 2000)
    private String propertyDescription;

    @Column(name = "property_type")
    private String propertyType;

    @Column(name = "property_status")
    private String propertyStatus;

    @Column(name = "reserve_type")
    private String reserveType;

    @Column(name = "reserve_price")
    private Float reservePrice;

    @Column(name = "max_capacity")
    private Integer maxCapacity;

    @Column(name = "sale_price")
    private Float salePrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publication_id")
    private Publication publication;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    private Address address;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PropertyPhoto> photos;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "property_amenities",
            joinColumns = @JoinColumn(name = "property_id"),
            inverseJoinColumns = @JoinColumn(name = "amenity_id")
    )
    private Set<Amenity> amenities;

    @JsonCreator
    public Property(
            @JsonProperty("property_id") Long propertyId,
            @JsonProperty("property_name") String propertyName,
            @JsonProperty("property_description") String propertyDescription,
            @JsonProperty("property_type") String propertyType,
            @JsonProperty("property_status") String propertyStatus,
            @JsonProperty("reserve_type") String reserveType,
            @JsonProperty("reserve_price") Float reservePrice,
            @JsonProperty("max_capacity") Integer maxCapacity,
            @JsonProperty("sale_price") Float salePrice,
            @JsonProperty("owner") User owner,
            @JsonProperty("publication") Publication publication,
            @JsonProperty("address") Address address,
            @JsonProperty("photos") Set<PropertyPhoto> photos,
            @JsonProperty("amenities") Set<Amenity> amenities
    )
    {
        this.propertyId = propertyId;
        this.propertyName = propertyName;
        this.propertyDescription = propertyDescription;
        this.propertyType = propertyType;
        this.propertyStatus = propertyStatus;
        this.reserveType = reserveType;
        this.reservePrice = reservePrice;
        this.maxCapacity = maxCapacity;
        this.salePrice = salePrice;
        this.owner = owner;
        this.publication = publication;
        this.address = address;
        this.photos = photos;
        this.amenities = amenities;
    }

    public Property() {}
}
