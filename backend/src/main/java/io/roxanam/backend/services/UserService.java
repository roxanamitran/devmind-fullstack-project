package io.roxanam.backend.services;

import io.roxanam.backend.entities.User;
import io.roxanam.backend.entities.UserType;
import io.roxanam.backend.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    public User save(User user) {
        user.setActive(true);
        return userRepository.save(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void deleteById(Long id) {
        User user = findById(id);
        user.setActive(false);
        userRepository.save(user);
    }

    public User update(User user) {
        if(!userRepository.existsByIdAndIsActiveTrue(user.getId())) {
            throw new RuntimeException("Can not update user.");
        }

        return save(user);
    }

    public List<User> findAllByType(UserType userType) {
        return userRepository.findAllByUserTypeAndIsActiveTrue(userType);
    }
}
