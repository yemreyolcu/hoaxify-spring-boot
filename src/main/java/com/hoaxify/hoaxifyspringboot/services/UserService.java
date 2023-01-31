package com.hoaxify.hoaxifyspringboot.services;

import com.hoaxify.hoaxifyspringboot.defaults.GenericResponse;
import com.hoaxify.hoaxifyspringboot.entities.User;
import com.hoaxify.hoaxifyspringboot.repositories.UserRepository;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Data
@Service
public class UserService {
    UserRepository userRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    public GenericResponse createUser(User user) {
        String encryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        userRepository.save(user);
        return new GenericResponse("User Created", user.getId(), user.getUsername());
    }

}
