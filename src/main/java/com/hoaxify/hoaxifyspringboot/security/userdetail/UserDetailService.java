package com.hoaxify.hoaxifyspringboot.security.userdetail;

import com.hoaxify.hoaxifyspringboot.api.entities.User;
import com.hoaxify.hoaxifyspringboot.api.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailService implements UserDetailsService {

    final UserRepository userRepository;

    public UserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public CustomUserDetail loadUserByUsername(String username) throws UsernameNotFoundException {
        User foundUser = userRepository.findByUsername(username);

        if (foundUser == null)
            throw new UsernameNotFoundException("User not found");
        return new CustomUserDetail(foundUser);
    }
}
