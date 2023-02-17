package com.hoaxify.hoaxifyspringboot.api.controllers;


import com.hoaxify.hoaxifyspringboot.api.defaults.GenericResponse;
import com.hoaxify.hoaxifyspringboot.api.defaults.errors.ApiError;
import com.hoaxify.hoaxifyspringboot.api.entities.dto.UserDto;
import com.hoaxify.hoaxifyspringboot.api.entities.dto.UserUpdateDto;
import com.hoaxify.hoaxifyspringboot.api.entities.model.User;
import com.hoaxify.hoaxifyspringboot.api.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/users")
public class UserController {

    // private static final Logger log = LoggerFactory.getLogger(UserController.class); show logs about project

    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public GenericResponse createUser(@Valid @RequestBody User user) {
        userService.createUser(user);
        return new GenericResponse("User Created", user.getId(), user.getUsername());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidationException(MethodArgumentNotValidException exception) {
        ApiError err = new ApiError(400, "ValidationError", "/api/users/create");
        Map<String, String> validationErrors = new HashMap<>();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) { // exception.getBindingResult().getFieldErrors all field errors list
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        err.setValidationErrors(validationErrors);
        return err;
    }

    @GetMapping("/")
    public Page<UserDto> usersList(Pageable page) {
        return userService.usersList(page).map(UserDto::new);
    }

    @GetMapping("/{username}")
    public UserDto userRetrieve(@PathVariable String username) {
        User foundUser = userService.userRetrieve(username);
        return new UserDto(foundUser);
    }

    @PutMapping("/{username}")
    @PreAuthorize("#username == principal.username") // username params is equal to request users username
    public UserDto userUpdate(@PathVariable String username, @RequestBody UserUpdateDto userUpdateDto) {
        User foundUser = userService.userUpdate(username, userUpdateDto);
        return new UserDto(foundUser);
    }
}
