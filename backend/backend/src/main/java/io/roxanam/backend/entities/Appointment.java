package io.roxanam.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;
    private Instant startDate;
    private Instant endDate;

    @ManyToOne
    @JoinColumn(name = "id_service_type")
    private ServiceType serviceType;

    @ManyToOne
    @JoinColumn(name = "id_salon")
    private Salon salon;

    @ManyToOne
    @JoinColumn(name = "id_client")
    private User client;

    @ManyToOne
    @JoinColumn(name = "id_employee")
    private User employee;
}
