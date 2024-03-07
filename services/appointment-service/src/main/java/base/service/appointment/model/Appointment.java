package base.service.appointment.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("appointments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Appointment {
    @Id
    private String id;
    @NotEmpty(message = "Invalid treatmentName:  empty treatment name")
    private String treatmentName;
    private String userId;
    @NotEmpty(message = "Invalid employee:  empty employee id")
    private String employeeId;
    @NotEmpty(message = "Invalid dateTime:  empty date")
    private Date dateTime;
}
