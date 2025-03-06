package com.example.diplom.repositories;

import com.example.diplom.model.Order;
import com.example.diplom.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
