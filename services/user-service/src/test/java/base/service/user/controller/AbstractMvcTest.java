package base.service.user.controller;

import base.service.user.model.Role;
import base.service.user.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.request.RequestPostProcessor;


import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@EnableGlobalMethodSecurity(prePostEnabled = true)
public abstract class AbstractMvcTest {
    @Autowired
    protected TestRestTemplate restTemplate;
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    protected RequestPostProcessor makeAuthorizedAdminUser() {
        return SecurityMockMvcRequestPostProcessors.user("admin@example.com").roles(Role.get(Role.ROLE_ADMIN));
    }

    protected RequestPostProcessor makeAuthorizedUser() {
        return SecurityMockMvcRequestPostProcessors.user("test@example.com").roles(Role.get(Role.ROLE_USER));
    }

}
