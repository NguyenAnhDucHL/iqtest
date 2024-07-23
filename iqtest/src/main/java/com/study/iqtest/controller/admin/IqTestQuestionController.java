package com.study.iqtest.controller.admin;

import com.study.iqtest.dto.IqTestQuestionDTO;
import com.study.iqtest.model.IqTestQuestion;
import com.study.iqtest.service.admin.IqTestQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/iqtest/questions")
public class IqTestQuestionController {

    @Autowired
    private IqTestQuestionService iqTestQuestionService;

    @PostMapping
    public ResponseEntity<IqTestQuestionDTO> createQuestion(@RequestBody IqTestQuestionDTO questionDTO) {
        return ResponseEntity.ok(iqTestQuestionService.createQuestion(questionDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IqTestQuestionDTO> updateQuestion(@PathVariable String id, @RequestBody IqTestQuestionDTO questionDTO) {
        return ResponseEntity.ok(iqTestQuestionService.updateQuestion(id, questionDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable String id) {
        iqTestQuestionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{settingId}")
    public ResponseEntity<List<IqTestQuestionDTO>> getQuestionsBySettingId(@PathVariable String settingId) {
        return ResponseEntity.ok(iqTestQuestionService.getQuestionsBySettingId(settingId));
    }
}
