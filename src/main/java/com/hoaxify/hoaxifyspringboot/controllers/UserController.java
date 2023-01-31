package com.hoaxify.hoaxifyspringboot.controllers;


import com.hoaxify.hoaxifyspringboot.defaults.GenericResponse;
import com.hoaxify.hoaxifyspringboot.defaults.errors.ApiError;
import com.hoaxify.hoaxifyspringboot.entities.User;
import com.hoaxify.hoaxifyspringboot.responses.UserCreateResponse;
import com.hoaxify.hoaxifyspringboot.services.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/users")
public class UserController {

    // private static final Logger log = LoggerFactory.getLogger(UserController.class); show logs about project

    @Autowired
    UserService userService;

    @PostMapping("/create")
    public GenericResponse createUser(@Valid @RequestBody User user) {
        userService.createUser(user);
        return new GenericResponse("User Created", user.getId(), user.getUsername());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidationException(MethodArgumentNotValidException exception) {
        ApiError err = new ApiError(400,"ValidationError", "/api/users/create");
        Map<String, String> validationErrors = new HashMap<>();
        for(FieldError fieldError: exception.getBindingResult().getFieldErrors()) { // exception.getBindingResult().getFieldErrors all field errors list
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        err.setValidationErrors(validationErrors);
        return err;
    }
}
