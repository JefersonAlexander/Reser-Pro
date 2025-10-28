package com.microservice.property.services;

import com.microservice.property.dtos.UserDto;
import com.microservice.property.entities.User;
import com.microservice.property.mappers.UserMapper;
import com.microservice.property.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public UserDto getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + username));
    }

    @Transactional
    public UserDto createUser(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    @Transactional
    public UserDto updateUser(Long id, UserDto userDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        existingUser.setUsername(userDto.getUsername());
        existingUser.setEmail(userDto.getEmail());

        User updatedUser = userRepository.save(existingUser);
        return userMapper.toDto(updatedUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}