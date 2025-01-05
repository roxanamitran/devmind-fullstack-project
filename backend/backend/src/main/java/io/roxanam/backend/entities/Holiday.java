package io.roxanam.backend.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Entity
@Data
public class Holiday {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Instant startDate;
    private Instant endDate;

    @ManyToOne
    @JoinColumn(name = "id_employee")
    private User employee;
}
