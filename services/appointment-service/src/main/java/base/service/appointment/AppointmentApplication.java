package base.service.appointment;

import base.service.appointment.repository.AppointmentRepository;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableMongoRepositories
@EnableScheduling
@OpenAPIDefinition(info =
@Info(title = "Appointment API", version = "1.0", description = "Documentation Appointment API v1.0")
)
public class AppointmentApplication {
    @Autowired
    AppointmentRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(AppointmentApplication.class, args);
    }

}
