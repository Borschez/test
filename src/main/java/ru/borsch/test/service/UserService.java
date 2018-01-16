package ru.borsch.test.service;

import ru.borsch.test.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User findById(Long id);

    User findByUsername(String username);

    User saveUser(User user);

    User updateUser(User user);

    void deleteUserById(Long id);

    List<User> findAllUsers();

    boolean isUserExist(User user);

    User getCurrentUser();
}
