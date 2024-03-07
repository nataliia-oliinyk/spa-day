package base.service.treatment.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "treatments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Treatment {

    @Id
    private String id;
    @NotEmpty(message = "The name is required.")
    @Size(min = 2, max = 100, message = "The length of  name must be between 2 and 100 characters.")
    private String name;
    private String description;
    private String image;
    @Positive(message = "The duration must be greater than 0.")
    private int durationInMinutes;

}
