package com.hoaxify.hoaxifyspringboot.api.repositories;

import com.hoaxify.hoaxifyspringboot.api.entities.model.Hoax;
import com.hoaxify.hoaxifyspringboot.api.entities.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HoaxRepository extends JpaRepository<Hoax, Long> {

    Page<Hoax> findByUser(User foundUser, Pageable page);
}
