package com.study.iqtest.service;

import com.study.iqtest.dto.UserDTO;
import java.util.Date;
import com.study.iqtest.model.User;
import com.study.iqtest.mapper.UserMapper;
import com.study.iqtest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public void registerUser(UserDTO userDTO) {
        userDTO.setCreatedAt(new Date());
        User user = userMapper.toModal(userDTO);
        userRepository.save(user);
    }

    public Optional<UserDTO> findByEmail(String email) {
        return userRepository.findByEmail(email).map(userMapper::toDto);
    }

}
