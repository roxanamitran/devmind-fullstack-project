package io.roxanam.backend.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class ServiceType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String photo;
    private Double price;
    private Integer duration;

    @ManyToOne
    @JoinColumn(name = "id_salon")
    private Salon salon;

    @ManyToMany
    @JoinTable(name = "employee_service_type",
    joinColumns = @JoinColumn(name = "employee_id"),
    inverseJoinColumns = @JoinColumn(name = "service_type_id"))
    private List<User> employees;
}
