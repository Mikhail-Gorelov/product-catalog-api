package com.example.productcatalog.entity;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.annotation.Id;
import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.UUID;

@Table("product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    private UUID id;
    private String name;
    private String description;
    private Double price;
    private String image;

    @Column("category_id")
    private UUID categoryId;
    private LocalDate dateAdded;
    @Builder.Default
    private boolean active = Boolean.FALSE;
}
