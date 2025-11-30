package com.reser_pro.auth_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.reser_pro.auth_service.DTO.LoginRequest;
import com.reser_pro.auth_service.DTO.LoginResponse;
import com.reser_pro.auth_service.DTO.RegisterRequest;
import com.reser_pro.auth_service.DTO.UserDTO;
import com.reser_pro.auth_service.DTO.UserLoginResponse;
import com.reser_pro.auth_service.entity.Role;
import com.reser_pro.auth_service.entity.User;
import com.reser_pro.auth_service.mappers.UserMapper;
import com.reser_pro.auth_service.repository.RoleRepository;
import com.reser_pro.auth_service.repository.UserRepository;
import com.reser_pro.auth_service.util.JwtUtil;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthAuditService auditService;

    public LoginResponse login(LoginRequest request) throws Exception {
        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        UserDetails userDetails = (UserDetails) auth.getPrincipal();

        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getIsActive()) {
            auditService.saveAudit(user, "LOGIN_USER_NOT_ACTIVE", "User: " + user.getUserName() + " is not active for login.");
            throw new Exception("User is not active!");
        }

        List<String> roles = user.getRoles()
            .stream()
            .map(Role::getRoleName)
            .toList();

        UserLoginResponse userLoginResponse = new UserLoginResponse(user.getUserId(), 
                                                                    user.getEmail(), 
                                                                    user.getUserName(),
                                                                    user.getPhoneNumber(), 
                                                                    roles);

        auditService.saveAudit(user, "LOGIN_SUCCESFULL", "User: " + user.getUserName() + " logged in.");

        return new LoginResponse(jwtUtil.generateToken(userDetails), userLoginResponse);
    }

    public UserDTO register(RegisterRequest request) throws Exception {
        User user = new User();

        if (userRepository.existsByEmail(request.getEmail())) {
            auditService.saveAudit(null, "REGISTER_WITH_EXISTING_EMAIL", "Email: " + request.getEmail() + " already exists.");
            throw new Exception("User already exists");
        }

        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUserName(request.getUserName());
        user.setPhoneNumber(request.getPhoneNumber());

        List<Role> roles = request.getRoles()
            .stream()
            .map(roleName -> roleRepository.findByRoleName(roleName)
                                .orElseThrow(() -> new RuntimeException("Role not found: " + roleName)))
            .toList();

        Boolean isAdmin = roles.stream()
            .anyMatch(role -> role.getRoleName().equals("ADMIN"));

        user.setRoles(roles);

        if (isAdmin) {
            user.setIsActive(true);
        } else {
            user.setIsActive(false);
        }

        user = userRepository.save(user);

        UserDTO userDTO = userMapper.toDTO(user);

        StringBuilder rolesString = new StringBuilder();

        for (String roleStr : userDTO.getRoles()) {
            rolesString.append(roleStr);
            rolesString.append(" ");
        }

        auditService.saveAudit(user, "REGISTER_SUCCESFUL", "User: " + user.getUserName() + " was registered as " + rolesString);

        return userMapper.toDTO(user);
    }
}
