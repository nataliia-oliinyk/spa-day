package base.service.user.controller;

import base.service.user.dto.AuthenticationRequest;
import base.service.user.dto.AuthenticationResponse;
import base.service.user.service.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DisplayName("Test AUTHENTICATION controller")
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Inject
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AuthenticationService authService;

    @Test
    void testAuthenticateUser() throws Exception {
        when(authService.authenticate(any(AuthenticationRequest.class))).thenReturn(new AuthenticationResponse("token1"));
        mockMvc.perform(post("/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(new AuthenticationRequest("test@example.com", "test11"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token1"));

        mockMvc.perform(post("/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(new AuthenticationRequest("test", "test"))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value((containsInAnyOrder("Invalid Password: must be between 6 and 10 symbols.","Invalid Email"))));
    }

    @Test
    void testRegisterUser() {
    }

}