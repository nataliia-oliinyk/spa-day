package base.service.appointment.service;

import base.service.appointment.dto.AppointmentRequest;
import base.service.appointment.dto.ByMonthYearRequest;
import base.service.appointment.dto.DateDto;
import base.service.appointment.model.Appointment;
import base.service.appointment.model.Employee;
import base.service.appointment.model.Treatment;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public interface AppointmentService {
    public List<Appointment> getByMonthYear(ByMonthYearRequest request);
    public List<Appointment> getByDate(Date date);
    public List<Appointment> getByUser(String userId);
    public Appointment update(String id, AppointmentRequest request);
    public Appointment cancel(String id);
    public List<Appointment> generateAppointmentsByMonthYear(ByMonthYearRequest request);
    public void generateAppointments(HashMap<DateDto, Date> dateMap);
}
