package com.study.iqtest.controller.admin;

import com.study.iqtest.model.IqTestSetting;
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

    @PostMapping
    public ResponseEntity<IqTestSetting> createSetting(@RequestBody IqTestSetting setting) {
        return ResponseEntity.ok(iqTestSettingService.createSetting(setting));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IqTestSetting> updateSetting(@PathVariable String id, @RequestBody IqTestSetting setting) {
        return ResponseEntity.ok(iqTestSettingService.updateSetting(id, setting));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSetting(@PathVariable String id) {
        iqTestSettingService.deleteSetting(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<IqTestSetting>> getAllSettings() {
        return ResponseEntity.ok(iqTestSettingService.getAllSettings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<IqTestSetting> getSettingById(@PathVariable String id) {
        return ResponseEntity.ok(iqTestSettingService.getSettingById(id));
    }

}
