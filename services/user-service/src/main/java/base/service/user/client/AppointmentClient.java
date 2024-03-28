package base.service.user.client;

import base.service.user.dto.AppointmentRequest;
import base.service.user.model.Appointment;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "appointment-service")
public interface AppointmentClient {

    @PostMapping("/{appointment_id}")
    public ResponseEntity<Appointment> applyPatch(@PathVariable("appointment_id") String appointment_id, @Valid @RequestBody AppointmentRequest request);

    @GetMapping("/{appointment_id}/cancel")
    public ResponseEntity<Appointment> cancel(@PathVariable("appointment_id") String appointment_id);

    @GetMapping("/user/{user_id}")
    public ResponseEntity<List<Appointment>> getByUserId(@PathVariable("user_id") String user_id);
}
