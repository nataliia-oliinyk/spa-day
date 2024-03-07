package base.service.appointment.controller;

import base.service.appointment.dto.ImageRequest;
import base.service.appointment.model.Employee;
import base.service.appointment.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = EmployeesController.class)
@AutoConfigureDataMongo
@DisplayName("Test EMPLOYEE controller")
class EmployeesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmployeeService employeeService;

    @Test
    void testFindAll() throws Exception {
        mockMvc.perform(get("/").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    void testGetById() throws Exception {
        Employee employee = new Employee("id-1", "test employee1", null, null);
        when(employeeService.getById(anyString())).thenReturn(employee);
        mockMvc.perform(get("/{id}", "id-1").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(employee.getId()));

        when(employeeService.getById(anyString())).thenThrow(new NotFoundException("Not found employee"));
        mockMvc.perform(get("/{id}", "id-").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors").value("Not found employee"));
    }

    @Test
    void testSaveEmployee() throws Exception {
        Employee employee = new Employee("id-2", "test employee2", null, null);
        when(employeeService.save(any(Employee.class))).thenReturn(employee);

        mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(employee.getId()));

        mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(new Employee(null, null, null, null))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value("Invalid name:  empty name"));

        mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(new Employee(null, "t", null, null))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value("The length of  name must be between 2 and 100 characters."));
    }

    @Test
    void testUpdateEmployee() throws Exception {
        Employee employee = new Employee("id-3", "test employee2", null, null);
        when(employeeService.update(anyString(), any(Employee.class))).thenReturn(employee);
        mockMvc.perform(put("/{id}", "id-3")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(employee.getId()));

        mockMvc.perform(put("/{id}", "id-3")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(new Employee(null, null, null, null))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value("Invalid name:  empty name"));

        mockMvc.perform(put("/{id}", "id-3")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(new Employee(null, "t", null, null))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value("The length of  name must be between 2 and 100 characters."));
    }

    @Test
    void addPhoto() throws Exception {
        Employee employee = new Employee("id-4", "test employee2", null, "new-image");
        ImageRequest imageRequest = new ImageRequest("new-image");

        when(employeeService.addImage(anyString(), any(ImageRequest.class))).thenReturn(employee);
        mockMvc.perform(post("/{id}/upload", "id-4")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(imageRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.image").value(imageRequest.getImage()));

        mockMvc.perform(post("/{id}/upload", "id-4")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post("/{id}/upload", "id-4")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(new ImageRequest(""))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value("Invalid image:  empty image"));

        when(employeeService.addImage(anyString(), any(ImageRequest.class))).thenThrow(new NotFoundException("Not found employee"));
        mockMvc.perform(post("/{id}/upload", "id-")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(imageRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors").value("Not found employee"));
    }
}