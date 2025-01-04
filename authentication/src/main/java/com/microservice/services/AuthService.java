package com.microservice.services;

import com.microservice.dto.Token;
import com.microservice.models.User;
import com.microservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + login);
        }
        return new org.springframework.security.core.userdetails.User(
                user.getLogin(),
                user.getPassword(),
                new ArrayList<>()
        );
    }

    public Token authenticate(String login, String password) {
        User user = userRepository.findByLogin(login);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return Token.builder()
                    .id(Math.toIntExact(user.getId()))
                    .build();
        }
        return null;
    }

    public User register(String login, String password) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }
}