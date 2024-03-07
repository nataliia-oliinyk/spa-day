package base.service.appointment.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ByMonthYearRequest {
    @NotEmpty(message = "Invalid month:  empty month")
    String month;
    @NotEmpty(message = "Invalid year:  empty year")
    String year;
}
