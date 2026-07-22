package com.example.productcatalog.entity;


import jakarta.persistence.Id;
import lombok.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("category")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private final UUID id = UUID.randomUUID();
    private String name;
    private String description;
}
