package io.roxanam.backend.controllers;

import io.roxanam.backend.dtos.LoginRequestDto;
import io.roxanam.backend.dtos.LoginResponseDto;
import io.roxanam.backend.dtos.UserDto;
import io.roxanam.backend.entities.User;
import io.roxanam.backend.entities.UserType;
import io.roxanam.backend.mappers.UserMapper;
import io.roxanam.backend.security.MyUserDetails;
import io.roxanam.backend.services.JwtService;
import io.roxanam.backend.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private UserService userService;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;

    @PostMapping
    public UserDto save(@RequestBody UserDto userDto) {
        return UserMapper.toDto(userService.save(UserMapper.toEntity(userDto)));
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequestDto login) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
            String token = jwtService.generate(((MyUserDetails) authenticate.getPrincipal()));
            return ResponseEntity.ok(new LoginResponseDto(token));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
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
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/available-employees")
    public List<UserDto> findAllAvailableEmployees() {
        List<User> users = userService.findAllAvailableEmployees();
        return users.stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }
}
