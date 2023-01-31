package com.hoaxify.hoaxifyspringboot.responses;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserCreateResponse {
    private Long id;
    private String username;
    private String displayName;
}
