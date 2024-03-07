package base.service.appointment.dto;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AppointmentRequest {
    @NotEmpty(message = "Invalid user: user id is empty")
    private String userId;
}
