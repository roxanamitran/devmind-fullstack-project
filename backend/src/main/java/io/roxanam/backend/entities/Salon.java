package io.roxanam.backend.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Entity
@Data
public class Salon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phoneNumber;
    private String email;
    private String address;
    private String city;
    private String photoUrl;
    private String description;
    @OneToMany
    private List<Schedule> schedules = new ArrayList<>();
    @OneToOne
    private User manager;
    @OneToMany
    private List<User> employees = new ArrayList<>();
    private boolean isActive;
    @OneToMany(mappedBy = "salon")
    private List<SalonToSalonOffer> salonToSalonOffers = new ArrayList<>();
}
