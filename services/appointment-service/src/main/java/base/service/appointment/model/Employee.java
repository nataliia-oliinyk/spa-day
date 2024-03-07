package base.service.appointment.model;

import lombok.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private String id;
    private String name;
    private List<String> treatments;
    private String image;
}
