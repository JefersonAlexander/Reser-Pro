package com.microservice.property.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@Getter
@Setter

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "username")
     private String username;

    @Column(name = "email")
     private String email;

    @OneToMany(mappedBy = "owner")
    private Set<Property> properties;

    @JsonCreator
    public User(
            @JsonProperty("userId") Long userId,
            @JsonProperty("username") String username,
            @JsonProperty("email") String email,
            @JsonProperty("properties") Set<Property> properties)
    {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.properties = properties;
    }

    public User() { }
}
