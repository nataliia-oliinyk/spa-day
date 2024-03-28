package base.service.user.controller;

import base.service.user.dto.AuthenticationResponse;
import base.service.user.dto.AuthenticationRequest;
import base.service.user.dto.RegisterRequest;
import base.service.user.dto.UpdateUserRequest;
import base.service.user.exceptions.UserExistsException;
import base.service.user.service.AuthenticationService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    AuthenticationService authService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticateUser(@Valid @RequestBody AuthenticationRequest request)  {
    	LOGGER.info(request.toString());
        return new ResponseEntity<>(authService.authenticate(request), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerUser(@Valid @RequestBody RegisterRequest request) throws UserExistsException {
    	LOGGER.info(request.toString());
    	return new ResponseEntity<>(authService.register(request), HttpStatus.CREATED);
    }
}
