package ru.otus.services;

import ru.otus.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAll();

    User addNewUser(User user);

    void deleteUser(Long id);

    Optional<User> getUserByName(String name);
}
