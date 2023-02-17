package com.hoaxify.hoaxifyspringboot.api.controllers;

import com.hoaxify.hoaxifyspringboot.api.defaults.GenericResponse;
import com.hoaxify.hoaxifyspringboot.api.defaults.errors.ApiError;
import com.hoaxify.hoaxifyspringboot.api.entities.dto.HoaxDto;
import com.hoaxify.hoaxifyspringboot.api.entities.dto.HoaxUpdateDto;
import com.hoaxify.hoaxifyspringboot.api.entities.model.Hoax;
import com.hoaxify.hoaxifyspringboot.api.entities.model.User;
import com.hoaxify.hoaxifyspringboot.api.services.HoaxService;
import com.hoaxify.hoaxifyspringboot.api.services.UserService;
import com.hoaxify.hoaxifyspringboot.security.userdetail.CustomUserDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/hoaxs")

public class HoaxController {



    final HoaxService hoaxService;
    final UserService userService;

    public HoaxController(HoaxService hoaxService, UserService userService) {
        this.hoaxService = hoaxService;
        this.userService = userService;
    }


    @PostMapping("/create")
    GenericResponse createHoax(@Valid @RequestBody Hoax hoax) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetail user = (CustomUserDetail) auth.getPrincipal();
        hoaxService.createHoax(hoax, user.getUser());
        return new GenericResponse("Hoax Created", hoax.getId(), user.getUsername());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidationException(MethodArgumentNotValidException exception) {
        ApiError err = new ApiError(400, "ValidationError", "/api/hoaxs/create");
        Map<String, String> validationErrors = new HashMap<>();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) { // exception.getBindingResult().getFieldErrors all field errors list
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        err.setValidationErrors(validationErrors);
        return err;
    }


    @GetMapping("/")
    Page<HoaxDto> hoaxsList(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable page) {
        return hoaxService.hoaxsList(page).map(HoaxDto::new);
    }

    @GetMapping("/{username}")
    Page<HoaxDto> hoaxsListFromUser(@PathVariable String username, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable page) {
        return hoaxService.hoaxsListFromUser(page, username).map(HoaxDto::new);
    }


    @DeleteMapping("/{hoaxId}")
    @PreAuthorize("@hoaxSecurityService.isAllowedToDelete(#hoaxId, principal)")
    GenericResponse deleteHoax(@PathVariable long hoaxId) {
        System.out.println("Log Controller" + hoaxId);
        hoaxService.deleteHoax(hoaxId);
        return new GenericResponse("Hoax Deleted", hoaxId, null);
    }

}
