package com.study.iqtest.controller.admin;

import com.study.iqtest.dto.IqTestResultDTO;
import com.study.iqtest.dto.UserProfileDTO;
import com.study.iqtest.service.admin.IqTestResultService;
import com.study.iqtest.service.admin.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/iqtest/results")
@Tag(name = "IQ Test Results", description = "API for managing IQ Test Results")
public class IqTestResultController {

    @Autowired
    private IqTestResultService iqTestResultService;

    @Autowired
    private UserProfileService userProfileService;

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Search IQ Test Results", description = "Search for IQ Test Results by text")
    public ResponseEntity<Page<IqTestResultDTO>> searchIqTestResults(
            @RequestParam(required = false) String searchText,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<IqTestResultDTO> results = iqTestResultService.searchIqTestResults(searchText, page, size);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Get User Profile", description = "Retrieve the user profile by user ID")
    public ResponseEntity<UserProfileDTO> getUserProfile(
            @PathVariable("userId") @Parameter(description = "ID of the user") ObjectId userId) {
        UserProfileDTO userProfile = userProfileService.getUserProfile(userId);
        return ResponseEntity.ok(userProfile);
    }
}