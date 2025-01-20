package io.roxanam.backend.controllers;

import io.roxanam.backend.dtos.UserDto;
import io.roxanam.backend.entities.User;
import io.roxanam.backend.entities.UserType;
import io.roxanam.backend.mappers.SalonMapper;
import io.roxanam.backend.mappers.UserMapper;
import io.roxanam.backend.repositories.UserRepository;
import io.roxanam.backend.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private UserService userService;

    @PostMapping
    public UserDto save(@RequestBody UserDto userDto) {
        return UserMapper.toDto(userService.save(UserMapper.toEntity(userDto)));
    }

    @GetMapping
    public List<UserDto> findAll() {
        List<User> users = userService.findAll();
        return users.stream().map(UserMapper::toDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserDto findById(@PathVariable("id") Long id) {
        return UserMapper.toDto(userService.findById(id));
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "Deleted.";
    }

    @PutMapping("/{id}")
    public UserDto update(@PathVariable("id") Long id, @RequestBody UserDto userDto) {
        if (id != userDto.getId()) {
            userDto.setId(id);
        }
        return UserMapper.toDto(userService.update(UserMapper.toEntity(userDto)));
    }

    @GetMapping("/by-type/{type}")
    public List<UserDto> findAllByType(@PathVariable("type") UserType userType) {
        List<User> users = userService.findAllByType(userType);
        return users.stream()
                .map(u -> UserMapper.toDto(u))
                .collect(Collectors.toList());
    }
}
