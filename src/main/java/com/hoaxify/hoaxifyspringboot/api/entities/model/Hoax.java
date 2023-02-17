package com.hoaxify.hoaxifyspringboot.api.entities.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hoaxes")
public class Hoax {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 1, max = 1000)
    @Column(length = 1000)
    @NotNull(message = "Content cannot be null")
    private String content;

    @ManyToOne
    private User user;

    private Date timestamp;
}
