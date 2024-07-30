package com.study.iqtest.controller.admin;

import com.study.iqtest.dto.IqTestResultDTO;
import com.study.iqtest.dto.UserProfileDTO;
import com.study.iqtest.service.admin.IqTestResultService;
import com.study.iqtest.service.admin.UserProfileService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/iqtest/results")
public class IqTestResultController {

    @Autowired
    private IqTestResultService iqTestResultService;

    @Autowired
    private UserProfileService userProfileService;

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Page<IqTestResultDTO>> searchIqTestResults(
            @RequestParam(required = false) String searchText,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<IqTestResultDTO> results = iqTestResultService.searchIqTestResults(searchText, page, size);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<UserProfileDTO> getUserProfile(@PathVariable("userId") ObjectId userId) {
        UserProfileDTO userProfile = userProfileService.getUserProfile(userId);
        return ResponseEntity.ok(userProfile);
    }
}
