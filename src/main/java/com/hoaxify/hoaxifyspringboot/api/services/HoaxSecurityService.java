package com.hoaxify.hoaxifyspringboot.api.services;


import java.util.Optional;

import com.hoaxify.hoaxifyspringboot.api.entities.model.Hoax;
import com.hoaxify.hoaxifyspringboot.api.entities.model.User;
import com.hoaxify.hoaxifyspringboot.api.repositories.HoaxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class HoaxSecurityService {

    @Autowired
    HoaxRepository hoaxRepository;

    public boolean isAllowedToDelete(long id, User loggedInUser) {
        /* Optional<Hoax> optionalHoax = hoaxRepository.findById(id);
        if (!optionalHoax.isPresent())
            return false;
        Hoax hoax = optionalHoax.get();
        if (loggedInUser.getId() !=  hoax.getUser().getId())
            return false;
        return true; */
        System.out.println("Logs : ");
        System.out.println(id);
        System.out.println(loggedInUser);
        return true;

    }
}
