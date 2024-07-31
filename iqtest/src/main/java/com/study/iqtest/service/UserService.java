package com.study.iqtest.service;

import com.study.iqtest.dto.UserDTO;
import com.study.iqtest.exception.InvalidRoleIdException;
import com.study.iqtest.mapper.UserMapper;
import com.study.iqtest.model.Role;
import com.study.iqtest.model.User;
import com.study.iqtest.repository.RoleRepository;
import com.study.iqtest.repository.UserRepository;
import lombok.Setter;
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
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public void registerUser(UserDTO userDTO) {
        String roleIdString = userDTO.getRoleId().toHexString();

        if (!ObjectId.isValid(roleIdString)) {
            throw new InvalidRoleIdException("Invalid role ID: " + roleIdString);
        }

        Role role = roleRepository.findById(roleIdString)
                .orElseThrow(() -> new InvalidRoleIdException("Role not found with ID: " + roleIdString));

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
}