package com.booking.resource_service.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "resources")
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String type;

    private boolean available;
}