package ru.borsch.test.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.borsch.test.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String userName);
}
