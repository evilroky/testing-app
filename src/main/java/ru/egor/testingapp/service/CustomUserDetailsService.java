package ru.egor.testingapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.egor.testingapp.entity.User;
import ru.egor.testingapp.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = new User();
            if (userRepository.findByUsername(username) != null) {
                user = userRepository.findByUsername(username);
            } else throw new NullPointerException();

            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .authorities(user.getRole().getName())
                    .build();
        } catch (Exception e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
}
