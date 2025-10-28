package com.microservice.property.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "property_photos")
@Data
@Getter
@Setter

public class PropertyPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "photo_id")
    private Long photoId;

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(name = "photo_name")
    private String photoName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @JsonCreator
    public PropertyPhoto(
            @JsonProperty("photo_id") Long photoId,
            @JsonProperty("photo_url") String photoUrl,
            @JsonProperty("photo_name") String photoName,
            @JsonProperty("property") Property property
    )
    {
        this.photoId = photoId;
        this.photoUrl = photoUrl;
        this.photoName = photoName;
        this.property = property;
    }

    public PropertyPhoto(){}
}
