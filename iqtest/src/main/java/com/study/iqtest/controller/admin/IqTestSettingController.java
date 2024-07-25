package com.study.iqtest.controller.admin;

import com.study.iqtest.dto.IqTestSettingDTO;
import com.study.iqtest.service.admin.IqTestSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import io.swagger.annotations.ApiOperation;

import java.util.List;

@RestController
@RequestMapping("/api/iqtest/settings")
@Api(value = "IQ Test Settings API", tags = {"IQ Test Settings"})
public class IqTestSettingController {

    @Autowired
    private IqTestSettingService iqTestSettingService;

    @PostMapping("/settings-with-questions")
    @ApiOperation(value = "Create a new setting with questions", response = IqTestSettingDTO.class)
    public ResponseEntity<IqTestSettingDTO> createSettingWithQuestions(@RequestBody IqTestSettingDTO settingDto) {
        return ResponseEntity.ok(iqTestSettingService.createSettingWithQuestions(settingDto));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update an existing setting", response = IqTestSettingDTO.class)
    public ResponseEntity<IqTestSettingDTO> updateSetting(@PathVariable String id, @RequestBody IqTestSettingDTO settingDto) {
        return ResponseEntity.ok(iqTestSettingService.updateSetting(id, settingDto));
    }

    @DeleteMapping("/{testSettingId}")
    @ApiOperation(value = "Delete a setting by ID")
    public ResponseEntity<Void> deleteSettingByTestSettingId(@PathVariable String testSettingId) {
        iqTestSettingService.deleteSettingById(testSettingId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @ApiOperation(value = "Get all settings", response = List.class)
    public ResponseEntity<List<IqTestSettingDTO>> getAllSettings() {
        return ResponseEntity.ok(iqTestSettingService.getAllSettings());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a setting by ID", response = IqTestSettingDTO.class)
    public ResponseEntity<IqTestSettingDTO> getSettingById(@PathVariable String id) {
        return ResponseEntity.ok(iqTestSettingService.getSettingById(id));
    }

}
