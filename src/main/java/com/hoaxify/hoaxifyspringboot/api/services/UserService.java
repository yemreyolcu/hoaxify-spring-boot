package com.hoaxify.hoaxifyspringboot.api.services;

import com.hoaxify.hoaxifyspringboot.api.entities.User;
import com.hoaxify.hoaxifyspringboot.api.repositories.UserRepository;
import com.hoaxify.hoaxifyspringboot.api.defaults.GenericResponse;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Data
@Service
public class UserService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public GenericResponse createUser(User user) {
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        userRepository.save(user);
        return new GenericResponse("User Created", user.getId(), user.getUsername());
    }

}
