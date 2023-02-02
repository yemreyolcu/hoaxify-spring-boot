package com.hoaxify.hoaxifyspringboot.api.repositories;

import com.hoaxify.hoaxifyspringboot.api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String s);
}
