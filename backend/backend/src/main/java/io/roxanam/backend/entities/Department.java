package io.roxanam.backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique=true)
    private String name;
    private String photo;

    @ManyToMany
    @JoinTable(name = "salon_department",
    joinColumns = @JoinColumn(name = "id_salon"),
    inverseJoinColumns = @JoinColumn(name = "id_department"))
    private List<Salon> salons;
}
