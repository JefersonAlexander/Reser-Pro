package com.reser_pro.auth_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.reser_pro.auth_service.DTO.EditRequest;
import com.reser_pro.auth_service.DTO.UserDTO;
import com.reser_pro.auth_service.entity.User;
import com.reser_pro.auth_service.mappers.UserMapper;
import com.reser_pro.auth_service.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AuthAuditService auditService;

    private static final String USER_NOT_FOUND_MSG = "User not found";
    
    public UserDTO getUserById(Long userId) {
        return userRepository.findById(userId)
                .map(userMapper::toDTO)
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND_MSG));
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDTO)
                .toList();
    }

    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException(USER_NOT_FOUND_MSG);
        }
        userRepository.deleteById(userId);
    }

    public void activateUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND_MSG));
        
        user.setIsActive(true);
        userRepository.save(user);

        String adminEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User admin = userRepository.findByEmail(adminEmail)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        auditService.saveAudit(admin, "USER_ACTIVATED", "Usuario: " + user.getUserName() + " activado por el admin: " + admin.getUserName());
    }

    public void desactivateUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND_MSG));

        user.setIsActive(false);
        userRepository.save(user);

        String adminEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User admin = userRepository.findByEmail(adminEmail)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        auditService.saveAudit(admin, "USER_DESACTIVATED", "Usuario: " + user.getUserName() + " desactivado por el admin: " + admin.getUserName());
    }

    public UserDTO editUser(Long userId, EditRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND_MSG));
        
        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }

        if (request.getUserName() != null) {
            user.setUserName(request.getUserName());
        }

        if (request.getPhoneNumber() != null) {
            user.setPhoneNumber(request.getPhoneNumber());
        }
        
        user = userRepository.save(user);
        
        return userMapper.toDTO(user);
    }
}
