package io.roxanam.backend.services;

import io.roxanam.backend.entities.Appointment;
import io.roxanam.backend.entities.Salon;
import io.roxanam.backend.entities.User;
import io.roxanam.backend.repositories.AppointmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AppointmentService {
    private AppointmentRepository appointmentRepository;
    private SalonService salonService;
    private SalonOfferService salonOfferService;
    private UserService userService;

    public Appointment save(Appointment appointment) {
        appointment.setActive(true);
        appointment.setSalon(salonService.findById(appointment.getSalon().getId()));
        appointment.setSalonOffer(salonOfferService.findById(appointment.getSalonOffer().getId()));
        appointment.setCustomer(userService.findById(appointment.getEmployee().getId()));
        appointment.setEmployee(userService.findById(appointment.getCustomer().getId()));
        return appointmentRepository.save(appointment);
    }

    public Appointment findById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Holiday with id " + id + " not found."));
    }

    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    public Appointment update(Appointment appointment) {
        if (!appointmentRepository.existsByIdAndIsActiveTrue(appointment.getId())) {
            throw new RuntimeException("Can not update appointment");
        }

        return save(appointment);
    }

    public void deleteById(Long id) {
        Appointment appointment = findById(id);
        appointment.setActive(false);
        appointmentRepository.save(appointment);
    }

    public List<Appointment> findAllByEmployee(Long employeeId) {
        User foundedEmployee = userService.findById(employeeId);

        return appointmentRepository.findAllByEmployeeAndIsActiveTrue(foundedEmployee);
    }

    public List<Appointment> findAllByCustomer(Long customerId) {
        User foundedCustomer = userService.findById(customerId);

        return appointmentRepository.findAllByCustomerAndIsActiveTrue(foundedCustomer);
    }

    public List<Appointment> findAllBySalon(Long salonId) {
        Salon foundedSalon = salonService.findById(salonId);

        return appointmentRepository.findAllBySalonAndIsActiveTrue(foundedSalon);
    }


}
