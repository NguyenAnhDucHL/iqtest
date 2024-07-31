package com.study.iqtest.controller;

import com.study.iqtest.dto.IqTestAnswerDTO;
import com.study.iqtest.dto.UserDTO;
import com.study.iqtest.service.student.IqTestService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.study.iqtest.service.admin.UserProfileService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;

@RestController
@RequestMapping("/api/iqtest")
@Tag(name = "IQ Test", description = "API for IQ Test operations such as starting, answering, and finishing tests")
public class IqTestController {

    @Autowired
    private IqTestService iqTestService;

    @Autowired
    private UserProfileService userProfileService;

    @PostMapping("/start")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_STUDENT')")
    @Operation(summary = "Start a new IQ test", description = "Starts a new session of IQ test for a user")
    public ResponseEntity<?> startIQTest(@RequestBody UserDTO user) {
        return ResponseEntity.ok(iqTestService.startTest(user));
    }

    @GetMapping("/{testSettingId}/questions")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_STUDENT')")
    @Operation(summary = "Get questions for a specific IQ test", description = "Retrieves questions for a specific IQ test setting")
    public ResponseEntity<?> getIQTestQuestions(@PathVariable("testSettingId") @Parameter(description = "ID of the IQ test setting") ObjectId testSettingId) {
        return ResponseEntity.ok(iqTestService.getQuestions(testSettingId));
    }

    @PostMapping("/{testId}/finish")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_STUDENT')")
    @Operation(summary = "Finish a specific IQ test", description = "Finalizes the IQ test session and processes the results")
    public ResponseEntity<?> finishIQTest(@PathVariable("testId") @Parameter(description = "ID of the IQ test") ObjectId testId, @RequestBody List<IqTestAnswerDTO> answers) {
        return ResponseEntity.ok(iqTestService.finishTest(testId, answers));
    }

    @GetMapping("/{testId}/result")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_STUDENT')")
    @Operation(summary = "Get the result of a specific IQ test", description = "Retrieves the result of a completed IQ test")
    public ResponseEntity<?> getIQTestResult(@PathVariable("testId") @Parameter(description = "ID of the IQ test") ObjectId testId) {
        return ResponseEntity.ok(iqTestService.getResult(testId));
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_STUDENT')")
    @Operation(summary = "Update user profile", description = "Update user profile by user ID")
    public ResponseEntity<UserDTO> updateUserProfile(
            @PathVariable("userId") @Parameter(description = "ID of the user to be updated") ObjectId userId,
            @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userProfileService.updateUserProfile(userId, userDTO);
        return ResponseEntity.ok(updatedUser);
    }

}