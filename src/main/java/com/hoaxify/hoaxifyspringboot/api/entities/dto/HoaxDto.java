package com.hoaxify.hoaxifyspringboot.api.entities.dto;

import com.hoaxify.hoaxifyspringboot.api.entities.model.Hoax;
import lombok.Data;

@Data
public class HoaxDto {
    private Long id;
    private String content;
    private long timestamp;
    private UserDto user;

    public HoaxDto(Hoax hoax) {
        this.setId(hoax.getId());
        this.setContent(hoax.getContent());
        this.setTimestamp(hoax.getTimestamp().getTime());
        this.setUser(new UserDto(hoax.getUser()));
    }
}
