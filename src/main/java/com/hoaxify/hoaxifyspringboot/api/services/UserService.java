package com.hoaxify.hoaxifyspringboot.api.services;

import com.hoaxify.hoaxifyspringboot.api.defaults.errors.NotFoundException;
import com.hoaxify.hoaxifyspringboot.api.entities.dto.UserUpdateDto;
import com.hoaxify.hoaxifyspringboot.api.entities.model.User;
import com.hoaxify.hoaxifyspringboot.api.repositories.UserRepository;
import com.hoaxify.hoaxifyspringboot.api.defaults.GenericResponse;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<User> usersList(Pageable page) {
        return userRepository.findAll(page);
    }

    public User userRetrieve(String username) {
        User foundUser = userRepository.findByUsername(username);
        if (foundUser != null)
            return foundUser;
        throw new NotFoundException();
    }

    public User userUpdate(String username, UserUpdateDto userUpdateDto) {
        User myUser = userRetrieve(username);
        myUser.setDisplayName(userUpdateDto.getDisplayName());
        return userRepository.save(myUser);
    }
}
