package com.example.diplom.repositories;

import com.example.diplom.model.Menu;
import com.example.diplom.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User, Long> {
    /**
     * Ищет пользователя по имени пользователя (логину).
     *
     * @param username Имя пользователя для поиска
     * @return Optional с пользователем, если найден
     */
    Optional<User> findByUsername(String username);
}
