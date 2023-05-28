package ru.kirill.hotelreserve.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kirill.hotelreserve.repository.UserRepository;
import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository
                .findByEmail(email)
                .map(us -> new User(
                        us.getEmail(),
                        us.getPassword(),
                        Collections.singleton(us.getRole())
                ))
                .orElseThrow(() -> new UsernameNotFoundException("Failed to load user with login: " + email));
        log.info("Credentials verified");
        return user;
    }
}
