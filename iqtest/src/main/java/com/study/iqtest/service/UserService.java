package com.study.iqtest.service;

import com.study.iqtest.dto.RoleDTO;
import com.study.iqtest.dto.UserDTO;
import com.study.iqtest.exception.InvalidRoleIdException;
import com.study.iqtest.mapper.RoleMapper;
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
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder,
                       RoleRepository roleRepository, RoleMapper roleMapper ) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
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

    public List<RoleDTO> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(roleMapper::toDto)
                .collect(Collectors.toList());
    }
}