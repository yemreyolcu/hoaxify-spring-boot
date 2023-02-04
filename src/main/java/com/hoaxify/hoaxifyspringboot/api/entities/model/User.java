package com.hoaxify.hoaxifyspringboot.api.entities.model;

import com.hoaxify.hoaxifyspringboot.api.defaults.annotations.UniqueUsername;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @UniqueUsername
    @NotNull(message = "Username cannot be null")
    @Size(min = 4, max = 25, message = "Username must be between 4 and 25 characters")
    private String username;

    @NotNull(message = "Display name cannot be null")
    @Size(min = 4, max = 25, message = "Display name must be between 4 and 25 characters")
    private String displayName;


    @NotNull(message = "Password cannot be null")
    @Size(min = 4, max = 25)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "Password must contain at least one uppercase letter, one lowercase letter and one number")
    private String password;
}
