package base.service.appointment.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "employees")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Employee {

    @Id
    private String id;
    @NotEmpty(message = "Invalid name:  empty name")
    @Size(min = 2, max = 100, message = "The length of  name must be between 2 and 100 characters.")
    private String name;
    private List<String> treatments;
    private String image;

}
