package ru.otus.services.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.domain.Role;
import ru.otus.domain.User;
import ru.otus.exeptions.EntityNotFoundException;
import ru.otus.repositories.RoleRepository;
import ru.otus.repositories.UserRepository;
import ru.otus.services.UserService;
import ru.otus.utils.RolesNames;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class UserServiceImpl implements UserService {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(Logger.class);

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    @HystrixCommand
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    @HystrixCommand
    public User addNewUser(User user) {
        Role role = roleRepository
                .findByRole(RolesNames.USER)
                .orElseThrow(() -> new javax.persistence.EntityNotFoundException("Role not found"));
        user.addRole(role);
        logger.info(String.format("try to add user with name %s and role %s", user.getName(), role.getRole()));
        return userRepository.save(user);
    }

    @Override
    @HystrixCommand
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("user not found"));
        userRepository.delete(user);
        logger.info(String.format("user with name %s was deleted", user.getName()));
    }

    @Override
    @HystrixCommand
    public Optional<User> getUserByName(String name) {
        return userRepository.findByName(name);
    }
}
