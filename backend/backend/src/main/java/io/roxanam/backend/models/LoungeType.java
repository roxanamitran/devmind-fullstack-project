package io.roxanam.backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoungeType {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique=true)
    private String name;
    private String photo;
}
