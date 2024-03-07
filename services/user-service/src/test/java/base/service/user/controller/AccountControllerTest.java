package base.service.user.controller;

import base.service.user.service.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Test AUTHENTICATION controller")
class AccountControllerTest extends AbstractMvcTest{
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AuthenticationService authService;

    @Test
    void testAccountIndexUnauthorized() throws Exception {
        mockMvc.perform(get("/account/").with(anonymous())).andExpect(status().isUnauthorized()).andDo(print());
    }

    @Test
    void testAccountIndexInvalidRole() throws Exception {
        mockMvc.perform(
                get("/account/")
                .with(makeAuthorizedAdminUser()))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    void testAccountIndex() throws Exception {
        mockMvc.perform(get("/account/").with(makeAuthorizedUser())).andExpect(status().isOk()).andDo(print());
    }
}