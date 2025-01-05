package io.roxanam.backend.services;

import io.roxanam.backend.dtos.AppointmentDto;
import io.roxanam.backend.entities.Appointment;
import io.roxanam.backend.exceptions.EntityNotFoundException;
import io.roxanam.backend.mappers.ServiceTypeMapper;
import io.roxanam.backend.repositories.AppointmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@AllArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final UserService userService;
    private final SalonService salonService;
    private final ServiceTypeService serviceTypeService;

    public Appointment findById(Integer id) {
       return appointmentRepository.findById(id)
               .orElseThrow(() -> new EntityNotFoundException("Appointment with id " + id + " not found"));
    }

    public List<Appointment> findAllByClientId(Integer clientId) {
        return appointmentRepository.findAllByClient_Id(clientId);
    }

    public List<Appointment> findAllByEmployeeId(Integer employeeId) {
        return appointmentRepository.findAllByEmployee_Id(employeeId);
    }

    public Appointment save(AppointmentDto appointmentDto) {
        Appointment appointment = new Appointment();
        appointment.setId(appointmentDto.getId());
        appointment.setStartDate(appointmentDto.getStartDate());
        appointment.setEndDate(appointmentDto.getStartDate().plus(appointmentDto.getServiceType().getDuration(), ChronoUnit.MINUTES));
        appointment.setServiceType(serviceTypeService.findById(appointmentDto.getServiceType().getId()));
        appointment.setStatus(appointmentDto.getStatus());
        appointment.setEmployee(userService.findById(appointmentDto.getEmployee().getId()));
        appointment.setClient(userService.findById(appointmentDto.getClient().getId()));
        appointment.setSalon(salonService.findById(appointmentDto.getSalon().getId()));

        return appointmentRepository.save(appointment);
    }

    public String deleteById(Integer id) {
        appointmentRepository.deleteById(id);
        return "Appointment deleted";
    }
}
