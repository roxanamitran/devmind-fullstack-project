package io.roxanam.backend.controllers;

import io.roxanam.backend.dtos.UserDto;
import io.roxanam.backend.entities.UserType;
import io.roxanam.backend.mappers.SalonMapper;
import io.roxanam.backend.mappers.UserMapper;
import io.roxanam.backend.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public UserDto findById(@PathVariable("id") Integer id) {
        return UserMapper.toDto(userService.findById(id));
    }

    @GetMapping
    public List<UserDto> findAll() {
        return userService.findAll().stream().map(UserMapper::toDto).collect(Collectors.toList());
    }

    @PostMapping
    public UserDto save(@RequestBody UserDto userDto){
        return UserMapper.toDto(userService.save(UserMapper.toEntity(userDto)));
    }

    @PutMapping("/{id}")
    public UserDto update(@PathVariable("id") Integer id, @RequestBody UserDto userDto) {
        if (id != userDto.getId()) {
            userDto.setId(id);
        }

        return UserMapper.toDto(userService.save(UserMapper.toEntity(userDto)));
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Integer id) {
        return userService.deleteById(id);
    }
}
