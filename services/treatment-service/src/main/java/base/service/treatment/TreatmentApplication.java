package base.service.treatment;

import base.service.treatment.repository.TreatmentRepository;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
@OpenAPIDefinition(info =
@Info(title = "Treatment API", version = "1.0", description = "Documentation Treatment API v1.0")
)
public class TreatmentApplication {

    @Autowired
    TreatmentRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(TreatmentApplication.class, args);
    }

}
