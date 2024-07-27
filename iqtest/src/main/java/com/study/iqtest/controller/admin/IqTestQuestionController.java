package com.study.iqtest.controller.admin;

import com.study.iqtest.dto.IqTestQuestionDTO;
import com.study.iqtest.service.admin.IqTestQuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/iqtest/questions")
@Tag(name = "IQ Test Questions", description = "API for managing IQ Test Questions")
public class IqTestQuestionController {

    @Autowired
    private IqTestQuestionService iqTestQuestionService;

    @PostMapping
    @Operation(summary = "Create a new question", description = "Create a new IQ Test Question")
    public ResponseEntity<IqTestQuestionDTO> createQuestion(@RequestBody IqTestQuestionDTO questionDTO) {
        return ResponseEntity.ok(iqTestQuestionService.createQuestion(questionDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing question", description = "Update an existing IQ Test Question using its ID")
    public ResponseEntity<IqTestQuestionDTO> updateQuestion(@PathVariable("id") @Parameter(description = "ID of the question to be updated") String id, @RequestBody IqTestQuestionDTO questionDTO) {
        return ResponseEntity.ok(iqTestQuestionService.updateQuestion(id, questionDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a question by ID", description = "Delete an IQ Test Question using its ID")
    public ResponseEntity<Void> deleteQuestionByQuestionId(@PathVariable("id") @Parameter(description = "ID of the question to be deleted") String id) {
        iqTestQuestionService.deleteQuestionByQuestionId(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{settingId}")
    @Operation(summary = "Get questions by setting ID", description = "Retrieve IQ Test Questions by the setting ID")
    public ResponseEntity<List<IqTestQuestionDTO>> getQuestionsBySettingId(@PathVariable("settingId") @Parameter(description = "Setting ID associated with the questions") String settingId) {
        return ResponseEntity.ok(iqTestQuestionService.getQuestionsBySettingId(settingId));
    }
}
