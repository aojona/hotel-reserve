package ru.kirill.hotelreserve.service;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kirill.hotelreserve.dto.UserRequest;
import ru.kirill.hotelreserve.dto.UserResponse;
import ru.kirill.hotelreserve.entity.User;
import ru.kirill.hotelreserve.mapper.UserMapper;
import ru.kirill.hotelreserve.repository.UserRepository;

import java.util.Collections;

@Service
public class UserService extends CRUDService<User,UserRequest,UserResponse,Long> implements UserDetailsService {

    private final UserRepository userRepository;
    public UserService(UserRepository userRepository, UserMapper mapper) {
        super(userRepository, mapper);
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        return userRepository
                .findByEmail(email)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getEmail(),
                        user.getPassword(),
                        Collections.singleton(user.getRole())
                ))
                .orElseThrow(() -> new UsernameNotFoundException("Failed to load user with login: " + email));
    }
}
