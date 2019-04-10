package ru.otus.services.impl;

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

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User addNewUser(User user) {
        Optional<Role> role = roleRepository.findByRole(RolesNames.USER);
        user.addRole(role);
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("user not found"));
        userRepository.delete(user);
    }

    @Override
    public Optional<User> getUserByName(String name) {
        return userRepository.findByName(name);
    }
}
