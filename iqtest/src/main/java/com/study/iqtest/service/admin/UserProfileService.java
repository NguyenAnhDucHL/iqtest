package com.study.iqtest.service.admin;

import com.study.iqtest.dto.IqTestResultDTO;
import com.study.iqtest.dto.UserDTO;
import com.study.iqtest.dto.UserProfileDTO;
import com.study.iqtest.exception.UserNotFoundException;
import com.study.iqtest.model.IqTest;
import com.study.iqtest.model.IqTestResult;
import com.study.iqtest.model.User;
import com.study.iqtest.repository.IqTestRepository;
import com.study.iqtest.repository.IqTestResultRepository;
import com.study.iqtest.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserProfileService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IqTestRepository iqTestRepository;

    @Autowired
    private IqTestResultRepository iqTestResultRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserProfileDTO getUserProfile(ObjectId userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));

        List<IqTest> iqTests = iqTestRepository.findByUserId(userId);
        List<ObjectId> testIds = iqTests.stream().map(IqTest::getId).collect(Collectors.toList());
        List<IqTestResult> iqTestResults = iqTestResultRepository.findByTestIdIn(testIds);

        List<IqTestResultDTO> iqTestResultDTOs = iqTestResults.stream()
                .map(result -> new IqTestResultDTO(
                        result.getId(),
                        result.getTestId(),
                        userId,
                        result.getScore(),
                        result.getResultDate(),
                        result.getFeedback(),
                        user.getName(),
                        user.getEmail(),
                        user.getPhoneNumber()
                ))
                .collect(Collectors.toList());

        return new UserProfileDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                iqTestResultDTOs
        );
    }

    public UserDTO updateUserProfile(ObjectId userId, UserDTO userDTO) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));

        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());

        // Encode the password before updating
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setUpdatedAt(new Date());
        userRepository.save(user);

        return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getPhoneNumber(),
                user.getRoleId(), user.getCreatedAt(), user.getUpdatedAt());
    }
}