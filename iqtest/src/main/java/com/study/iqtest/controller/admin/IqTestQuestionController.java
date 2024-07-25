package com.study.iqtest.controller.admin;

import com.study.iqtest.dto.IqTestQuestionDTO;
import com.study.iqtest.service.admin.IqTestQuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/iqtest/questions")
@Api(value = "IQ Test Questions API", tags = {"IQ Test Questions"})
public class IqTestQuestionController {

    @Autowired
    private IqTestQuestionService iqTestQuestionService;

    @PostMapping
    @ApiOperation(value = "Create a new question", response = IqTestQuestionDTO.class)
    public ResponseEntity<IqTestQuestionDTO> createQuestion(@RequestBody IqTestQuestionDTO questionDTO) {
        return ResponseEntity.ok(iqTestQuestionService.createQuestion(questionDTO));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update an existing question", response = IqTestQuestionDTO.class)
    public ResponseEntity<IqTestQuestionDTO> updateQuestion(@PathVariable String id, @RequestBody IqTestQuestionDTO questionDTO) {
        return ResponseEntity.ok(iqTestQuestionService.updateQuestion(id, questionDTO));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a question by ID")
    public ResponseEntity<Void> deleteQuestionByQuestionId(@PathVariable String id) {
        iqTestQuestionService.deleteQuestionByQuestionId(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{settingId}")
    @ApiOperation(value = "Get questions by setting ID", response = List.class)
    public ResponseEntity<List<IqTestQuestionDTO>> getQuestionsBySettingId(@PathVariable String settingId) {
        return ResponseEntity.ok(iqTestQuestionService.getQuestionsBySettingId(settingId));
    }
}
