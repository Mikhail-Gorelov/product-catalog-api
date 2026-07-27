package com.example.productcatalog.entity;


import org.springframework.data.annotation.Id;
import lombok.*;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("category")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category implements Persistable<UUID> {
    @Id
    @Column("id")
    private UUID id;
    private String name;
    private String description;

    @Override
    public boolean isNew() {
        if (id == null) {
            id = UUID.randomUUID();
            return true;
        }
        return false;
    }
}
