package io.roxanam.backend.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class SalonToSalonOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Salon salon;
    @ManyToOne
    private SalonOffer salonOffer;
    private Double price;
    private Integer duration;
    private boolean isActive;
}
