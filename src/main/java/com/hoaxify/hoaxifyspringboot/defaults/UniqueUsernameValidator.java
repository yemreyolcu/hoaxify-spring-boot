package com.hoaxify.hoaxifyspringboot.defaults;

import com.hoaxify.hoaxifyspringboot.defaults.annotations.UniqueUsername;
import com.hoaxify.hoaxifyspringboot.entities.User;
import com.hoaxify.hoaxifyspringboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        User user = userRepository.findByUsername(s);
        return user == null;
    }
}
