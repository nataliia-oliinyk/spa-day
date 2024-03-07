package base.service.user.controller;

import base.service.user.client.AppointmentClient;
import base.service.user.dto.AppointmentRequest;
import base.service.user.dto.AuthenticationResponse;
import base.service.user.dto.UpdateUserRequest;
import base.service.user.dto.UserResponse;
import base.service.user.exceptions.UserExistsException;
import base.service.user.model.Appointment;
import base.service.user.model.User;
import base.service.user.service.AuthenticationService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    AuthenticationService authService;
    @Autowired
    AppointmentClient client;

    @GetMapping("/")
    public ResponseEntity<UserResponse> getUser() {
        return new ResponseEntity<>(authService.getAuthUser(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<AuthenticationResponse> updateUser(@Valid @RequestBody UpdateUserRequest request) throws UserExistsException {
        return new ResponseEntity<>(authService.updateAuthUser(request), HttpStatus.OK);
    }

    @GetMapping("/{user_id}/appointments")
    public ResponseEntity<List<Appointment>> getAppointmentsByUserId(@PathVariable("user_id") String user_id){
        User user = authService.authUser();
        if (!user_id.equals(user.getId())){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return client.getByUserId(user.getId());
    }

    @PatchMapping("/appointments/{appointment_id}")
    public ResponseEntity<Appointment> applyAppointmentPatch(@PathVariable("appointment_id") String appointment_id, @Valid @RequestBody AppointmentRequest request){
        LOGGER.info(appointment_id);
        return client.applyPatch(appointment_id, request);
    }

    @GetMapping("/appointments/{appointment_id}/cancel")
    public ResponseEntity<Appointment> cancelAppointment(@PathVariable("appointment_id") String appointment_id){
        LOGGER.info(appointment_id);
        return client.cancel(appointment_id);
    }
}
