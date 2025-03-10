package com.example.diplom.service;

import com.example.diplom.model.User;
import com.example.diplom.model.Role;
import com.example.diplom.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Реализация UserDetailsService для загрузки пользовательских данных.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    /**
     * Загружает пользователя по имени пользователя.
     *
     * @param username имя пользователя
     * @return UserDetails пользователя
     * @throws UsernameNotFoundException если пользователь не найден
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRoles().stream()
                        .map(Role::getName)
                        .toArray(String[]::new))
                .build();
    }
}