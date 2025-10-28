package com.microservice.property.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "addresses")
@Getter
@Setter

public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "address_id")
    private Long addressId;

    @Column(name = "country")
    private String country;

    @Column(name = "state")
    private String state;

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    @Column(name = "latitude")
    private Float latitude;

    @Column(name = "longitud")
    private Float longitud;

    @OneToOne(mappedBy = "address")
    private Property property;

    @JsonCreator
    public Address(
            @JsonProperty("address_id") Long addressId,
            @JsonProperty("country") String country,
            @JsonProperty("state") String state,
            @JsonProperty("city") String city,
            @JsonProperty("street") String street,
            @JsonProperty("latitude") Float latitude,
            @JsonProperty("longitud") Float longitud,
            @JsonProperty("property") Property property
    )
    {
        this.addressId = addressId;
        this.country = country;
        this.state = state;
        this.city = city;
        this.street = street;
        this.latitude = latitude;
        this.longitud = longitud;
        this.property = property;
    }

    public Address(){}
}
