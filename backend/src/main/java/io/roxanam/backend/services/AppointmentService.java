package io.roxanam.backend.services;

import io.roxanam.backend.dtos.TimeSlotsDto;
import io.roxanam.backend.entities.*;
import io.roxanam.backend.repositories.AppointmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public Appointment save(Appointment appointment) {

        appointment.setStatus(AppointmentStatus.ACCEPTED);
        appointment.setSalon(salonService.findById(appointment.getSalon().getId()));
        appointment.setSalonToSalonOffer(salonToSalonOfferService.findById(appointment.getSalonToSalonOffer().getId()));
        appointment.setCustomer(userService.findById(appointment.getCustomer().getId()));
        appointment.setEmployee(userService.findById(appointment.getEmployee().getId()));
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

    public List<Appointment> findAllByEmployeeId(Long employeeId) {
        return appointmentRepository.findAllByEmployeeId(employeeId);
    }

    public List<Appointment> findAllByCustomerId(Long customerId) {
        return appointmentRepository.findAllByCustomerId(customerId);
    }

    public List<Appointment> findAllByEmployeeEmail(String email) {
        return appointmentRepository.findAllByEmployeeEmail(email);
    }

    public List<Appointment> findAllByCustomerEmail(String email) {
        return appointmentRepository.findAllByCustomerEmail(email);
    }

    public List<Appointment> findAllByManagerEmail(String email) {
        return appointmentRepository.findAllBySalonManagerEmail(email);
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

        LocalDate localDate = date.atZone(ZoneId.of("Europe/Bucharest")).toLocalDate();

        Schedule schedule = schedules.stream()
                .filter(s -> s.getDay().equals(getWeekDay(localDate.getDayOfWeek())))
                .findFirst()
                .orElse(null);

        if (schedule == null) {
            return Collections.emptyList();
        }

        LocalTime startHour = LocalTime.parse(schedule.getStartHour(), TIME_FORMATTER);
        LocalTime endHour = LocalTime.parse(schedule.getEndHour(), TIME_FORMATTER);


        List<LocalTime> allTimeslots = new ArrayList<>();
        LocalTime currentSlot = startHour;
        while (currentSlot.isBefore(endHour)) {
            allTimeslots.add(currentSlot);
            currentSlot = currentSlot.plusMinutes(30);
        }


        Instant endDate = date.plus(24, ChronoUnit.HOURS);
        System.out.println(endDate);
        List<Appointment> employeeAppointments = appointmentRepository.findAllByEmployeeIdAndStartDateBetweenAndStatus(employeeId, date, endDate, AppointmentStatus.ACCEPTED);

        List<LocalTime> unavailableTimeslotsBasedOnAppointment = employeeAppointments.stream()
                .flatMap(a -> {
                    LocalTime startTime = a.getStartDate().atZone(ZoneId.of("Europe/Bucharest")).toLocalTime();
                    LocalTime endTime = a.getEndDate().atZone(ZoneId.of("Europe/Bucharest")).toLocalTime();
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

        Set<LocalTime> unavailableTimeslotsBasedOnDuration = new HashSet<>();
        for(LocalTime localTime : unavailableTimeslotsBasedOnAppointment) {
            int slotDuration = 30;
            while (slotDuration < timeSlots.getDuration()) {
                unavailableTimeslotsBasedOnDuration.add(localTime.minusMinutes(slotDuration));
                slotDuration += 30;
            }
        }


        allTimeslots.removeAll(unavailableTimeslotsBasedOnAppointment);
        allTimeslots.removeAll(unavailableTimeslotsBasedOnDuration);

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

    public void deleteById(Long id) {
        appointmentRepository.deleteById(id);
    }
}



