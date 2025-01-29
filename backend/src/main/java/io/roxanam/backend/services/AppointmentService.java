package io.roxanam.backend.services;

import io.roxanam.backend.dtos.TimeSlotsDto;
import io.roxanam.backend.entities.*;
import io.roxanam.backend.repositories.AppointmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class AppointmentService {
    private AppointmentRepository appointmentRepository;
    private SalonService salonService;
    private SalonToSalonOfferService salonToSalonOfferService;
    private UserService userService;
    private HolidayService holidayService;

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static final ZoneId DEFAULT_ZONE_ID = ZoneId.systemDefault(); // Use system default or specify desired zone

    public Appointment save(Appointment appointment) {
        appointment.setStatus(AppointmentStatus.PENDING);
        appointment.setSalon(salonService.findById(appointment.getSalon().getId()));
        appointment.setSalonToSalonOffer(salonToSalonOfferService.findById(appointment.getSalonToSalonOffer().getId()));
        appointment.setCustomer(userService.findById(appointment.getEmployee().getId()));
        appointment.setEmployee(userService.findById(appointment.getCustomer().getId()));
        appointment.setEndDate(appointment.getStartDate().plus(appointment.getSalonToSalonOffer().getDuration(), ChronoUnit.MINUTES));
        return appointmentRepository.save(appointment);
    }

    public Appointment findById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Holiday with id " + id + " not found."));
    }

    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }



    public Appointment changeStatus(Long id, AppointmentStatus appointmentStatus) {
        Appointment appointment = findById(id);
        appointment.setStatus(appointmentStatus);
        return appointmentRepository.save(appointment);
    }

    public List<Appointment> findAllByEmployee(Long employeeId) {
        return appointmentRepository.findAllByEmployeeId(employeeId);
    }

    public List<Appointment> findAllByCustomer(Long customerId) {
        User foundedCustomer = userService.findById(customerId);

        return appointmentRepository.findAllByCustomer(foundedCustomer);
    }

    public List<Appointment> findAllBySalon(Long salonId) {
        Salon foundedSalon = salonService.findById(salonId);

        return appointmentRepository.findAllBySalon(foundedSalon);
    }

    public List<LocalTime> calculateAvailableTimeslots(
            TimeSlotsDto timeSlots) {
        Long salonId = timeSlots.getSalonId();
        Instant date = timeSlots.getDate();
        Long employeeId = timeSlots.getEmployeeId();

        Salon salon = salonService.findById(salonId);
        List<Schedule> schedules = salon.getSchedules();

        LocalDate localDate = date.atZone(DEFAULT_ZONE_ID).toLocalDate();

        // Get the employee's schedule for the given day
        Schedule schedule = schedules.stream()
                .filter(s -> s.getDay().equals(getWeekDay(localDate.getDayOfWeek())))
                .findFirst()
                .orElse(null);

        if (schedule == null) {
            return Collections.emptyList(); // No schedule defined for this day
        }

        // Check if it's a holiday for the employee
        List<Holiday> holidays = holidayService.findAllByEmployee(employeeId);
        if (holidays.stream()
                .anyMatch(h -> {
                    LocalDate holidayStartDate = h.getStartDate().atZone(DEFAULT_ZONE_ID).toLocalDate();
                    LocalDate holidayEndDate = h.getEndDate().atZone(DEFAULT_ZONE_ID).toLocalDate();
                    return localDate.isAfter(holidayStartDate) && localDate.isBefore(holidayEndDate) && h.isActive();
                })) {
            return Collections.emptyList(); // Employee is on holiday
        }

        LocalTime startHour = LocalTime.parse(schedule.getStartHour(), TIME_FORMATTER);
        LocalTime endHour = LocalTime.parse(schedule.getEndHour(), TIME_FORMATTER);

        // Create a list of all possible timeslots in 30-minute intervals
        List<LocalTime> allTimeslots = new ArrayList<>();
        LocalTime currentSlot = startHour;
        while (currentSlot.isBefore(endHour)) {
            allTimeslots.add(currentSlot);
            currentSlot = currentSlot.plusMinutes(30);
        }

        // Get all appointments for the employee on the given date
        Instant startOfDay = date.atZone(DEFAULT_ZONE_ID).toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant endOfDay = date.atZone(DEFAULT_ZONE_ID).toLocalDate().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant();
        List<Appointment> employeeAppointments = appointmentRepository.findAllByEmployeeIdAndStartDateBetween(employeeId, startOfDay, endOfDay);

        // Remove timeslots occupied by appointments
        List<LocalTime> unavailableTimeslots = employeeAppointments.stream()
                .flatMap(a -> {
                    LocalTime startTime = a.getStartDate().atZone(DEFAULT_ZONE_ID).toLocalTime();
                    LocalTime endTime = a.getEndDate().atZone(DEFAULT_ZONE_ID).toLocalTime();
                    List<LocalTime> unavailableSlots = new ArrayList<>();
                    LocalTime current = startTime;
                    while (current.isBefore(endTime)) {
                        unavailableSlots.add(current);
                        current = current.plusMinutes(30);
                    }
                    return unavailableSlots.stream();
                })
                .distinct()
                .toList();

        // Remove unavailable timeslots from all possible timeslots
        allTimeslots.removeAll(unavailableTimeslots);

        return allTimeslots;
    }

    private Day getWeekDay(DayOfWeek dayOfWeek) {
        switch (dayOfWeek) {
            case MONDAY -> {
                return Day.LUNI;
            }
            case TUESDAY -> {
                return Day.MARTI;
            }
            case WEDNESDAY -> {
                return Day.MIERCURI;
            }
            case THURSDAY -> {
                return Day.JOI;
            }
            case FRIDAY -> {
                return Day.VINERI;
            }
            case SATURDAY -> {
                return Day.SAMBATA;
            }
            default -> {
                return Day.DUMINICA;
            }
        }
    }
}



