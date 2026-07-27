package com.example.productcatalog.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.UUID;

@Table("product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product implements Persistable<UUID> {
    @Id
    @Column("id")
    private UUID id;
    private String name;
    private String description;
    private Double price;
    private String image;

    @NotNull
    @JsonProperty("category_id")
    @Column("category_id")
    private UUID categoryId;

    @Column("dateadded")
    private LocalDate dateAdded;


    @Builder.Default
    private boolean active = Boolean.FALSE;

    @Override
    public boolean isNew() {
        if (id == null) {
            id = UUID.randomUUID();
            return true;
        }
        return false;
    }

}
