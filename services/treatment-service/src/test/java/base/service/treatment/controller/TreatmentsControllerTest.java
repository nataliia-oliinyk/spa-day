package base.service.treatment.controller;

import base.service.treatment.dto.ImageRequest;
import base.service.treatment.model.Treatment;
import base.service.treatment.service.TreatmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TreatmentsController.class)
@AutoConfigureDataMongo
@DisplayName("Test TREATMENTS controller")
class TreatmentsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TreatmentService treatmentService;

    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get("/").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void testAddNew() throws Exception {
        Treatment treatment = new Treatment(null, "treatment1", "treatment desc", null, 10);
        when(treatmentService.save(any(Treatment.class))).thenReturn(treatment);
        mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(treatment)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("treatment1"));


        mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(new Treatment(null, null, null, null, 10))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value("The name is required."));

        mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(new Treatment(null, "test", null, null, 0))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value("The duration must be greater than 0."));
    }

    @Test
    void testGetByName() throws Exception {
        Treatment treatment = new Treatment(null, "treatment1", "treatment desc", null, 10);
        when(treatmentService.getByName(anyString())).thenReturn(treatment);
        mockMvc.perform(get("/{name}", "treatment1").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("treatment1"));

        when(treatmentService.getByName(anyString())).thenThrow(new NotFoundException("Not found treatment"));
        mockMvc.perform(get("/{name}", "treatment2").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors").value("Not found treatment"));
    }

    @Test
    void testUpdate() throws Exception {
        Treatment treatment = new Treatment(null, "treatment1", "treatment desc", null, 10);
        when(treatmentService.update(anyString(), any(Treatment.class))).thenReturn(treatment);

        mockMvc.perform(put("/{name}", "treatment1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(treatment)))
                .andExpect(status().isOk());

        mockMvc.perform(put("/{name}", "treatment1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(new Treatment(null, null, null, null, 0))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value(containsInAnyOrder("The duration must be greater than 0.","The name is required.")));;

        mockMvc.perform(put("/{name}", "treatment1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(new Treatment(null, "test", null, null, 0))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value("The duration must be greater than 0."));
    }

    @Test
    void addPhoto() throws Exception {
        when(treatmentService.addImage(anyString(), any(ImageRequest.class))).thenReturn(new Treatment(null, "treatment1", "treatment desc", null, 10));
        mockMvc.perform(post("/{name}/upload", "treatment1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(new ImageRequest("new-image"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("treatment1"));

        mockMvc.perform(post("/{name}/upload", "treatment2")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(new ImageRequest(""))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value("Invalid image: empty image"));

        when(treatmentService.addImage(anyString(), any(ImageRequest.class))).thenThrow(new NotFoundException("Not found treatment"));
        mockMvc.perform(post("/{name}/upload", "treatment3")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(new ImageRequest("new-image"))))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors").value("Not found treatment"));
    }
}