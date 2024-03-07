package base.service.appointment.controller;

import base.service.appointment.dto.AppointmentRequest;
import base.service.appointment.dto.ByMonthYearRequest;
import base.service.appointment.dto.DateDto;
import base.service.appointment.model.Appointment;
import base.service.appointment.service.AppointmentService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
public class AppointmentsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppointmentsController.class);

    @Autowired
    AppointmentService  service;

    @PostMapping("/")
    public ResponseEntity<Iterable<Appointment>> getByMothYear(@Valid @RequestBody ByMonthYearRequest request) {
        return new ResponseEntity<>(service.getByMonthYear(request), HttpStatus.OK);
    }

    @PostMapping("/{appointment_id}")
    public ResponseEntity<Appointment> applyPatch(@PathVariable("appointment_id") String appointment_id, @Valid @RequestBody AppointmentRequest request) {
        return new ResponseEntity<>(service.update(appointment_id, request), HttpStatus.OK);
    }

    @GetMapping("/{appointment_id}/cancel")
    public ResponseEntity cancel(@PathVariable("appointment_id") String appointment_id) {
        service.cancel(appointment_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/generate")
    public ResponseEntity<Iterable<Appointment>> generate(@Valid @RequestBody ByMonthYearRequest request) {
        return new ResponseEntity<>(service.generateAppointmentsByMonthYear(request), HttpStatus.OK);
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<List<Appointment>> getByUserId(@PathVariable("user_id") String userId) {
        return new ResponseEntity<>(service.getByUser(userId), HttpStatus.OK);
    }
}
