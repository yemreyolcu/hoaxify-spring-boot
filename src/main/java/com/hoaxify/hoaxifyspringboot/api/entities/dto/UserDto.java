package com.hoaxify.hoaxifyspringboot.api.entities.dto;

import com.hoaxify.hoaxifyspringboot.api.entities.model.User;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String displayName;

    public UserDto(User user) {
        this.setId(user.getId());
        this.setUsername(user.getUsername());
        this.setDisplayName(user.getDisplayName());
    }
}
