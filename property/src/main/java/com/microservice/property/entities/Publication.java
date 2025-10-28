package com.microservice.property.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Entity
@Table(name = "publications")
@Data
@Getter
@Setter

public class Publication {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "publication_id")
    private Long publicationId;

    @Column(name = "publication_type")
    private String publicationType;

    @OneToMany(mappedBy = "publication")
    private Set<Property> properties;

    @JsonCreator
    public Publication(
            @JsonProperty("publication_id") Long publicationId,
            @JsonProperty("publication_type") String publicationType
    )
    {
        this.publicationId = publicationId;
        this.publicationType = publicationType;
    }

    public Publication() { }
}
