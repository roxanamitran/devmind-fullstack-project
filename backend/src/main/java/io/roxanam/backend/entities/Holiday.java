package io.roxanam.backend.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Entity
@Data
public class Holiday {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Instant startDate;
    private Instant endDate;
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private User employee;
}
