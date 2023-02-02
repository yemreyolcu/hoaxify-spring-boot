package com.hoaxify.hoaxifyspringboot.security.jwt.http;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private Long id;
    private String token;
    private String username;
}
