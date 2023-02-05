package com.hoaxify.hoaxifyspringboot.api.services;

import com.hoaxify.hoaxifyspringboot.api.entities.model.Hoax;
import com.hoaxify.hoaxifyspringboot.api.entities.model.User;
import com.hoaxify.hoaxifyspringboot.api.repositories.HoaxRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class HoaxService {

    final HoaxRepository hoaxRepository;
    final UserService userService;

    public HoaxService(HoaxRepository hoaxRepository, UserService userService) {
        this.hoaxRepository = hoaxRepository;
        this.userService = userService;
    }

    public void createHoax(Hoax hoax, User user) {
        hoax.setTimestamp(new Date());
        hoax.setUser(user);
        hoaxRepository.save(hoax);
    }

    public Page<Hoax> hoaxsList(Pageable page) {
        return hoaxRepository.findAll(page);
    }

    public Page<Hoax> hoaxsListFromUser(Pageable page, String username) {
        User foundUser = userService.userRetrieve(username);
        return hoaxRepository.findByUser(foundUser, page);
    }
}
