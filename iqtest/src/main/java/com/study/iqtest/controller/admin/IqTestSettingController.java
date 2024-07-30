package com.study.iqtest.controller.admin;

import com.study.iqtest.dto.IqTestSettingDTO;
import com.study.iqtest.service.admin.IqTestSettingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;

@RestController
@RequestMapping("/api/iqtest/settings")
@Tag(name = "IQ Test Settings", description = "API for managing IQ Test Settings")
public class IqTestSettingController {

    @Autowired
    private IqTestSettingService iqTestSettingService;

    @PostMapping("/settings-with-questions")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Create a new setting with questions", description = "Add a new IQ Test Setting along with questions")
    public ResponseEntity<IqTestSettingDTO> createSettingWithQuestions(@RequestBody IqTestSettingDTO settingDto) {
        return ResponseEntity.ok(iqTestSettingService.createSettingWithQuestions(settingDto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Update an existing setting", description = "Update an existing IQ Test Setting using its ID")
    public ResponseEntity<IqTestSettingDTO> updateSetting(@PathVariable("id") @Parameter(description = "ID of the setting to be updated") ObjectId id, @RequestBody IqTestSettingDTO settingDto) {
        return ResponseEntity.ok(iqTestSettingService.updateSetting(id, settingDto));
    }

    @DeleteMapping("/{testSettingId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Delete a setting by ID", description = "Delete an IQ Test Setting using its ID")
    public ResponseEntity<Void> deleteSettingByTestSettingId(@PathVariable("testSettingId") @Parameter(description = "ID of the setting to be deleted") ObjectId testSettingId) {
        iqTestSettingService.deleteSettingById(testSettingId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Get all settings", description = "Retrieve all IQ Test Settings")
    public ResponseEntity<List<IqTestSettingDTO>> getAllSettings() {
        return ResponseEntity.ok(iqTestSettingService.getAllSettings());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Get a setting by ID", description = "Retrieve an IQ Test Setting by its ID")
    public ResponseEntity<IqTestSettingDTO> getSettingById(@PathVariable("id") @Parameter(description = "ID of the setting") ObjectId id) {
        return ResponseEntity.ok(iqTestSettingService.getSettingById(id));
    }
}
