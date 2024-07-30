package com.study.iqtest.service;

import com.study.iqtest.dto.UserDTO;
import com.study.iqtest.exception.InvalidRoleIdException;
import com.study.iqtest.mapper.UserMapper;
import com.study.iqtest.model.Role;
import com.study.iqtest.model.User;
import com.study.iqtest.repository.RoleRepository;
import com.study.iqtest.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    private static final Logger logger = Logger.getLogger(UserService.class.getName());

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        boolean matches = passwordEncoder.matches(rawPassword, encodedPassword);
        logger.info("Checking password: rawPassword=" + rawPassword + ", encodedPassword=" + encodedPassword + ", matches=" + matches);
        return matches;
    }

    public void registerUser(UserDTO userDTO) {
        String roleIdString = userDTO.getRoleId().toHexString();

        if (!ObjectId.isValid(roleIdString)) {
            throw new InvalidRoleIdException("Invalid role ID: " + roleIdString);
        }

        Role role = roleRepository.findById(roleIdString)
                .orElseThrow(() -> new InvalidRoleIdException("Role not found with ID: " + roleIdString));

        // Tạo một đối tượng User từ UserDTO
        User user = userMapper.toModal(userDTO);
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        user.setPassword(encodedPassword);
        user.setRoleId(role.getId());
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());

        userRepository.save(user);
    }

    public Optional<UserDTO> findByEmail(String email) {
        return userRepository.findByEmail(email).map(userMapper::toDto);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        Role role = roleRepository.findById(user.getRoleId().toString())
                .orElseThrow(() -> new IllegalStateException("Role not found"));

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.getRoleName().toUpperCase());
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(authority));
    }
}
