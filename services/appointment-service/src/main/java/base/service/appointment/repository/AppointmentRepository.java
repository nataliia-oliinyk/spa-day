package base.service.appointment.repository;

import base.service.appointment.model.Appointment;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface AppointmentRepository extends CrudRepository<Appointment, String> {

    public List<Appointment> findByDateTimeBetween(Date from, Date to);
    public List<Appointment> findByUserId(String userId);
}
