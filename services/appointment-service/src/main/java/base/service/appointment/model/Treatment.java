package base.service.appointment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Treatment {
    private String id;
    private String name;
    private String description;
    private String image;
    private int durationInMinutes;

}
