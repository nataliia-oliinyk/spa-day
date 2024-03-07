package base.service.appointment.service;

import base.service.appointment.model.Treatment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class TreatmentServiceImpl implements TreatmentService {
    @Value("${sharing_data.url_treatment}")
    private String treatmentUrl;

    @Autowired
    WebClient.Builder webClient;

    @Override
    public List<Treatment> getAll() {
        System.out.println(treatmentUrl);
        return webClient.build()
                .get()
                .uri(treatmentUrl + "/")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Treatment>>(){})
                .block();
    }
}
