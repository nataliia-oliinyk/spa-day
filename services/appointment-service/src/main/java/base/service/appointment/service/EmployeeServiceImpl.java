package base.service.appointment.service;

import base.service.appointment.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Value("${sharing_data.url_employee}")
    private String employeeUrl;

    @Autowired
    WebClient.Builder webClient;

    @Override
    public List<Employee> getAll() {
        return webClient.build()
                .get()
                .uri(employeeUrl + "/")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Employee>>(){})
                .block();
    }
}
