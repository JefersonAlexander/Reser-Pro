package com.reser_pro.auth_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reser_pro.auth_service.DTO.EditRequest;
import com.reser_pro.auth_service.DTO.UserDTO;
import com.reser_pro.auth_service.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/get-user-by-id")
    public ResponseEntity<UserDTO> getUserById(@RequestParam Long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @GetMapping("/get-all-users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @DeleteMapping("/delete-user")
    public ResponseEntity<?> deleteUser(@RequestParam Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted succesfully");
    }

    @PutMapping("/activate-user")
    public ResponseEntity<?> activateUser(@RequestParam Long userId) {
        userService.activateUser(userId);
        return ResponseEntity.ok("User activated succesfully");
    }

    @PutMapping("/edit-user")
    public ResponseEntity<UserDTO> editUser(@RequestParam Long userId, @RequestBody EditRequest request) {
        return ResponseEntity.ok(userService.editUser(userId, request));
    }
}
