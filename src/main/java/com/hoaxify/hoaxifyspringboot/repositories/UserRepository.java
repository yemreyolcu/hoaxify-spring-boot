package com.hoaxify.hoaxifyspringboot.repositories;

import com.hoaxify.hoaxifyspringboot.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String s);
}
