package com.study.iqtest.controller.admin;

import com.study.iqtest.dto.IqTestSettingDTO;
import com.study.iqtest.service.admin.IqTestSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/iqtest/settings")
public class IqTestSettingController {

    @Autowired
    private IqTestSettingService iqTestSettingService;

    @PostMapping("/settings-with-questions")
    public ResponseEntity<IqTestSettingDTO> createSettingWithQuestions(@RequestBody IqTestSettingDTO settingDto) {
        return ResponseEntity.ok(iqTestSettingService.createSettingWithQuestions(settingDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IqTestSettingDTO> updateSetting(@PathVariable String id, @RequestBody IqTestSettingDTO settingDto) {
        return ResponseEntity.ok(iqTestSettingService.updateSetting(id, settingDto));
    }

    @DeleteMapping("/{testSettingId}")
    public ResponseEntity<Void> deleteSettingByTestSettingId(@PathVariable String testSettingId) {
        iqTestSettingService.deleteSettingById(testSettingId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<IqTestSettingDTO>> getAllSettings() {
        return ResponseEntity.ok(iqTestSettingService.getAllSettings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<IqTestSettingDTO> getSettingById(@PathVariable String id) {
        return ResponseEntity.ok(iqTestSettingService.getSettingById(id));
    }

}
