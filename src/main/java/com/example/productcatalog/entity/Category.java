package com.example.productcatalog.entity;


import org.springframework.data.annotation.Id;
import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("category")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
    @Id
    private UUID id;
    private String name;
    private String description;
}
