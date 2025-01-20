package io.roxanam.backend.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Entity
@Data
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;
    private Instant startDate;
    private Instant endDate;
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "salon_id", nullable = false)
    private Salon salon;

    @ManyToOne
    @JoinColumn(name = "salon_offer_id", nullable = false)
    private SalonOffer salonOffer;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private User employee;
}
