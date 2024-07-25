package com.study.iqtest.controller;

import com.study.iqtest.dto.UserDTO;
import com.study.iqtest.dto.PasswordResetTokenDTO;
import com.study.iqtest.security.JwtUtil;
import com.study.iqtest.service.EmailService;
import com.study.iqtest.service.PasswordResetTokenService;
import com.study.iqtest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@Api(value = "User Management API", tags = {"User Management"})
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordResetTokenService passwordResetTokenService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    @ApiOperation(value = "Register a new user", response = ResponseEntity.class)
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        if (userService.findByEmail(userDTO.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email is already in use");
        }
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userService.registerUser(userDTO);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    @ApiOperation(value = "Login a user", response = ResponseEntity.class)
    public ResponseEntity<?> loginUser(@RequestBody UserDTO userDTO) {
        Optional<UserDTO> userOpt = userService.findByEmail(userDTO.getEmail());
        if (userOpt.isPresent()) {
            UserDTO user = userOpt.get();
            if (userService.checkPassword(userDTO.getPassword(), user.getPassword())) {
                final String jwt = jwtUtil.generateToken(user.getEmail());
                return ResponseEntity.ok(jwt);
            } else {
                return ResponseEntity.status(401).body("Invalid email or password");
            }
        }
        return ResponseEntity.status(401).body("Invalid email or password");
    }

    @PostMapping("/forgot-password")
    @ApiOperation(value = "Request a password reset", response = ResponseEntity.class)
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
}
