package base.service.user.model;

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
    private String treatmentName;
    private String userId;
    private String employeeId;
    private Date dateTime;
}
