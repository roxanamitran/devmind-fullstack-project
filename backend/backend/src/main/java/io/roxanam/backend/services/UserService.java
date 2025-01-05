package io.roxanam.backend.services;

import io.roxanam.backend.entities.User;
import io.roxanam.backend.entities.UserType;
import io.roxanam.backend.exceptions.EntityNotFoundException;
import io.roxanam.backend.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with id: " + id + " not found"));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> findAllByUserType(UserType userType) {
        return userRepository.findAllByUserType(userType);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public String deleteById(Integer id) {
        userRepository.deleteById(id);
        return "User deleted.";
    }
}
