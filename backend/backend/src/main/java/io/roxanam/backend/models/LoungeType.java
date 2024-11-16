package io.roxanam.backend.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class LoungeType {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique=true)
    private String name;
    private String photo;
}
