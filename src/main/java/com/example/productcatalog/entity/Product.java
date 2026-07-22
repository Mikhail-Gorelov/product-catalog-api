package com.example.productcatalog.entity;

import org.springframework.data.relational.core.mapping.Table;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Table("product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id = UUID.randomUUID();
    private String name;
    private String description;
    private Double price;
    private String image;

    @NotNull
    @ManyToOne(optional = false)
    private Category category;
    private LocalDate dateAdded;
    @Builder.Default
    private boolean active = Boolean.FALSE;
}
