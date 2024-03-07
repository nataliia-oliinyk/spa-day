package base.service.user.service;

import base.service.user.dto.*;
import base.service.user.exceptions.UserExistsException;
import base.service.user.model.User;
import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    private AuthenticationResponse authenticationResponseUser(User user){
        return new AuthenticationResponse(jwtService.generateToken(user));
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        return  authenticationResponseUser(userService.loadByEmail(request.getEmail()));
    }

    public AuthenticationResponse register(RegisterRequest request) throws UserExistsException {
        return authenticationResponseUser(userService.createUser(request));
    }

    public User authUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String email = ((UserDetails) principal).getUsername();
            return userService.loadByEmail(email);
        }
        throw new NotFoundException("");
    }

    public UserResponse getAuthUser() {
        User user = authUser();
        return new UserResponse(user.getId(),user.getEmail(), user.getName());
    }

    public AuthenticationResponse updateAuthUser(UpdateUserRequest request) throws UserExistsException {
        UserResponse userResponse = getAuthUser();
        User user = userService.updateUser(userResponse.getEmail(), request);
        return authenticationResponseUser(user);
    }
}
