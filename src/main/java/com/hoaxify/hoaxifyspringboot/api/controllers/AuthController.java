package com.hoaxify.hoaxifyspringboot.api.controllers;

import com.hoaxify.hoaxifyspringboot.api.defaults.GenericResponse;
import com.hoaxify.hoaxifyspringboot.api.defaults.errors.ApiError;
import com.hoaxify.hoaxifyspringboot.api.entities.model.User;
import com.hoaxify.hoaxifyspringboot.api.exceptions.SQLExceptionHandler;
import com.hoaxify.hoaxifyspringboot.api.services.UserService;
import com.hoaxify.hoaxifyspringboot.security.jwt.http.AuthRequest;
import com.hoaxify.hoaxifyspringboot.security.jwt.http.AuthResponse;
import com.hoaxify.hoaxifyspringboot.security.jwt.service.JwtUtil;
import com.hoaxify.hoaxifyspringboot.security.userdetail.CustomUserDetail;
import com.hoaxify.hoaxifyspringboot.security.userdetail.UserDetailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    final JwtUtil jwtUtil;
    final AuthenticationManager authenticationManager;
    final UserDetailService userDetailService;
    final UserService userService;

    public AuthController(JwtUtil jwtUtil, AuthenticationManager authenticationManager, UserDetailService userDetailService, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userDetailService = userDetailService;
        this.userService = userService;
    }

    @Value("${jwt.expire.hours}")
    private Long expireHours;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException exception) {
            throw new Exception("Incorrect username or password", exception);
        }
        final CustomUserDetail userDetail = userDetailService.loadUserByUsername(authRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetail, expireHours);
        return new AuthResponse(userDetail.getUser().getId(), jwt, userDetail.getUsername());
    }

    @PostMapping("/register")
    public GenericResponse register(@Valid @RequestBody User user) {
        GenericResponse addedUser;
        try {
            addedUser = userService.createUser(user);
        } catch (DataIntegrityViolationException ex) {
            throw new SQLExceptionHandler("There is already this user, try again.");
        }
        return new GenericResponse("User Created", addedUser.getId(), addedUser.getUsername());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidationException(MethodArgumentNotValidException exception) {
        ApiError err = new ApiError(400,"ValidationError", "/api/auth/register");
        Map<String, String> validationErrors = new HashMap<>();
        for(FieldError fieldError: exception.getBindingResult().getFieldErrors()) { // exception.getBindingResult().getFieldErrors all field errors list
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        err.setValidationErrors(validationErrors);
        return err;
    }
}
