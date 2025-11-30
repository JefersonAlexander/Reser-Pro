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

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Operation(summary = "Obtener usuario por id", description = "Retorna el usuario con el id ingresado")
    @GetMapping("/get-user-by-id")
    public ResponseEntity<UserDTO> getUserById(@RequestParam Long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @Operation(summary = "Obtener todos los usuarios", description = "Retorna la lista de todos los usuarios registrados")
    @GetMapping("/get-all-users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Operation(summary = "Eliminar usuario", description = "Elimina el usuario con el id ingresado")
    @DeleteMapping("/delete-user")
    public ResponseEntity<?> deleteUser(@RequestParam Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted succesfully");
    }

    @Operation(summary = "Activar usuario", description = "Permite a un administrador activar un usuario inactivo")
    @PutMapping("/activate-user")
    public ResponseEntity<?> activateUser(@RequestParam Long userId) {
        userService.activateUser(userId);
        return ResponseEntity.ok("User activated succesfully");
    }

    @Operation(summary = "Desactivar usuario", description = "Permite a un administrador desactivar un usuario activo")
    @PutMapping("/desactivate-user")
    public ResponseEntity<?> desactivateUser(@RequestParam Long userId) {
        userService.desactivateUser(userId);
        return ResponseEntity.ok("User desactivated succesfully");
    }

    @Operation(summary = "Editar usuario", description = "Retorna el usuario con a información actualizada")
    @PutMapping("/edit-user")
    public ResponseEntity<UserDTO> editUser(@RequestParam Long userId, @RequestBody EditRequest request) {
        return ResponseEntity.ok(userService.editUser(userId, request));
    }
}
