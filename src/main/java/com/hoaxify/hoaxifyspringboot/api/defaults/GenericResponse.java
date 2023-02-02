package com.hoaxify.hoaxifyspringboot.api.defaults;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GenericResponse {
    private String message;
    private Long id;
    private String username;
}
