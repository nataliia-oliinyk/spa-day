package base.service.user.service;

import base.service.user.dto.RegisterRequest;
import base.service.user.dto.UpdateUserRequest;
import base.service.user.dto.UserResponse;
import base.service.user.exceptions.UserExistsException;
import base.service.user.model.Role;
import base.service.user.model.User;
import base.service.user.repository.UserRepository;
import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User loadByEmail(String email) {
        if (!userRepository.existsByEmail(email)) {
            throw new NotFoundException("Invalid Email: not found user");
        }
        return userRepository.findByEmail(email).get();
    }

    public User createUser(RegisterRequest request) throws UserExistsException {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserExistsException("Invalid Email: duplicate");
        }
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.ROLE_USER);
        return userRepository.save(user);
    }

    public User updateUser(String email, UpdateUserRequest request) throws UserExistsException {
        User user = loadByEmail(email);
        if (request.getEmail() != null && !request.getEmail().isEmpty()){
            if (!request.getEmail().equals(email)){
                if (userRepository.existsByEmail(request.getEmail())) {
                    throw new UserExistsException("Invalid Email: duplicate");
                }
            }
            user.setEmail(request.getEmail());
        }
        if (request.getName() != null && !request.getName().isEmpty()){
            user.setName(request.getName());
        }
        if (request.getPassword() != null && !request.getPassword().isEmpty()){
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        return userRepository.save(user);
    }

}
