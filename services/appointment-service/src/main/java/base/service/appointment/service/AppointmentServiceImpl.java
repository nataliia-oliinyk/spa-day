package base.service.appointment.service;

import base.service.appointment.dto.AppointmentRequest;
import base.service.appointment.dto.ByMonthYearRequest;
import base.service.appointment.dto.DateDto;
import base.service.appointment.model.Appointment;
import base.service.appointment.model.Employee;
import base.service.appointment.model.Treatment;
import base.service.appointment.repository.AppointmentRepository;
import base.service.appointment.utilit.PatcherUtility;
import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import base.service.appointment.utilit.DateUtility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final Random random = new Random();

    @Value("${appointments.max_per_month}")
    private int maxPerMonth;
    @Autowired
    AppointmentRepository repository;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    DateUtility dateUtility;
    @Autowired
    PatcherUtility patcher;

    private Appointment findById(String id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Appointment not found with id " + id));
    }

    @Override
    public List<Appointment> getByMonthYear(ByMonthYearRequest request) {
        try {
            String formattedDate = String.format("%s-%s-01", request.getYear(), request.getMonth());
            Date date = dateFormat.parse(formattedDate);
            return getByDate(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Appointment> getByDate(Date date) {
        HashMap<DateDto, Date> dateMap = dateUtility.getFirstLastDayOfMonth(date);
        return repository.findByDateTimeBetween(dateMap.get(DateDto.FIRST_DAY), dateMap.get(DateDto.LAST_DAY));
    }

    @Override
    public List<Appointment> getByUser(String userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public Appointment update(String id, AppointmentRequest request) {
        Appointment appointment = findById(id);
        Appointment updatingAppointment = new Appointment();
        updatingAppointment.setUserId(request.getUserId());
        try {
            patcher.objectPatcher(Appointment.class, appointment, updatingAppointment);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return repository.save(appointment);
    }

    @Override
    public Appointment cancel(String id) {
        Appointment appointment = findById(id);
        appointment.setUserId(null);
        return repository.save(appointment);
    }

    @Override
    public List<Appointment> generateAppointmentsByMonthYear(ByMonthYearRequest request) {
        if (getByMonthYear(request).size() > 1){
            return getByMonthYear(request);
        }
        try {
            String formattedDate = String.format("%s-%s-01", request.getYear(), request.getMonth());
            HashMap<DateDto, Date> dateMap = dateUtility.getFirstLastDayOfNextMonth(dateFormat.parse(formattedDate), 0);
            System.out.println(dateMap.toString());
            generateAppointments(dateMap);
            return getByMonthYear(request);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void generateAppointments(HashMap<DateDto, Date> dateMap) {
        List<Employee> employees = employeeService.getAll();
        for (Employee employee : employees) {
            generateByEmployee(employee, dateMap);
        }
    }

    private void generateByEmployee(Employee employee, HashMap<DateDto, Date> dateMap) {
        for (int i = 0; i < maxPerMonth; i++) {
            Appointment appointment = new Appointment();
            appointment.setEmployeeId(employee.getId());
            appointment.setDateTime(dateUtility.randomDateMonth(dateMap.get(DateDto.FIRST_DAY), dateMap.get(DateDto.LAST_DAY)));
            appointment.setTreatmentName(randomTreatmentName(employee.getTreatments()));
            repository.save(appointment);
        }
    }

    private String randomTreatmentName(List<String> treatments) {
        return treatments.get(random.nextInt(treatments.size()));
    }
}
