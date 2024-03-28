package base.service.user.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegisterRequest {
    @NotEmpty(message = "Invalid Name: empty name")
    @Size(min = 2, max = 100, message = "Invalid Name: must be between 2 and 100 characters.")
    private String name;
    @Email(message = "Invalid Email", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;
    @NotEmpty(message = "Invalid Password: empty password")
    @Size(min = 6, max = 10, message = "Invalid Password: must be between 6 and 10 symbols.")
    private String password;
}


