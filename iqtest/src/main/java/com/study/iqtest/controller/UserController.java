package com.study.iqtest.controller;

import com.study.iqtest.dto.RoleDTO;
import com.study.iqtest.dto.UserDTO;
import com.study.iqtest.dto.PasswordResetTokenDTO;
import com.study.iqtest.model.Role;
import com.study.iqtest.repository.RoleRepository;
import com.study.iqtest.security.JwtUtil;
import com.study.iqtest.service.EmailService;
import com.study.iqtest.service.PasswordResetTokenService;
import com.study.iqtest.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@Tag(name = "User Management", description = "API for User Management activities such as registration, login, and password resets")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordResetTokenService passwordResetTokenService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Registers a new user and encodes their password")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        if (userService.findByEmail(userDTO.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email is already in use");
        }
        userService.registerUser(userDTO);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    @Operation(summary = "Login a user", description = "Logs in a user and returns a JWT token")
    public ResponseEntity<?> loginUser(@RequestBody UserDTO userDTO) {
        Optional<UserDTO> userOpt = userService.findByEmail(userDTO.getEmail());
        if (userOpt.isPresent()) {
            UserDTO user = userOpt.get();
            if (userService.checkPassword(userDTO.getPassword(), user.getPassword())) {
                String roleName = "ROLE_" + roleRepository.findById(user.getRoleId().toHexString())
                        .orElseThrow(() -> new IllegalStateException("Role not found"))
                        .getRoleName().toUpperCase();
                final String jwt = jwtUtil.generateToken(user.getEmail(), user.getEmail(), roleName);
                return ResponseEntity.ok(jwt);
            } else {
                return ResponseEntity.status(401).body("Invalid email or password");
            }
        }
        return ResponseEntity.status(401).body("Invalid email or password");
    }

    @PostMapping("/forgot-password")
    @Operation(summary = "Request a password reset", description = "Initiates a password reset process and sends a reset link to the user's email")
    public ResponseEntity<?> forgotPassword(@RequestBody PasswordResetTokenDTO param) {
        Optional<UserDTO> userOptional = userService.findByEmail(param.getEmail());
        if (userOptional.isPresent()) {
            UserDTO user = userOptional.get();
            String token = UUID.randomUUID().toString();

            PasswordResetTokenDTO resetTokenDTO = new PasswordResetTokenDTO(token, user.getEmail(), LocalDateTime.now().plusMinutes(15));
            passwordResetTokenService.save(resetTokenDTO);

            String resetLink = "http://iqtest.com/reset-password?token=" + token;

            String emailContent = "<html><body>" +
                    "<h2>Reset your password</h2>" +
                    "<p>We heard that you lost your password. Sorry about that!</p>" +
                    "<p>But don’t worry! You can use the following button to reset your password:</p>" +
                    "<a href=\"" + resetLink + "\" style=\"display: inline-block; padding: 10px 20px; font-size: 16px; color: #ffffff; background-color: #28a745; text-decoration: none; border-radius: 5px;\">Reset your password</a>" +
                    "<p>If you don’t use this link within 15 minutes, it will expire. To get a new password reset link, visit: <a href=\"http://iqtest.com/password_reset\">http://iqtest.com/password_reset</a></p>" +
                    "<p>Thanks,<br>The IQTest Team</p>" +
                    "</body></html>";

            // Send email
            emailService.sendMessage(param.getEmail(), "Password Reset", emailContent);

            return ResponseEntity.ok("Password reset link sent to email");
        }
        return ResponseEntity.badRequest().body("Email not found");
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout a user", description = "Logs out the currently authenticated user")
    public ResponseEntity<?> logoutUser(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
            SecurityContextHolder.clearContext();
        }
        return ResponseEntity.ok("User logged out successfully");
    }

    @GetMapping("/roles")
    @Operation(summary = "Get all roles", description = "Retrieve all roles from the database")
    public ResponseEntity<List<RoleDTO>> getAllRoles() {
        List<RoleDTO> roles = userService.getAllRoles();
        return ResponseEntity.ok(roles);
    }
}