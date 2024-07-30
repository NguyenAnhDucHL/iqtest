package com.study.iqtest.controller.admin;

import com.study.iqtest.dto.IqTestAnswerDTO;
import com.study.iqtest.model.IqTestAnswer;
import com.study.iqtest.service.admin.IqTestAnswerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/iqtest/answers")
@Tag(name = "IQ Test Answers", description = "API for IQ Test Answers")
public class IqTestAnswerController {

    @Autowired
    private IqTestAnswerService iqTestAnswerService;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Create a new answer", description = "Add a new IQ Test Answer to the database")
    public ResponseEntity<IqTestAnswerDTO> createAnswer(@RequestBody IqTestAnswerDTO answerDTO) {
        IqTestAnswer answer = iqTestAnswerService.createAnswer(answerDTO);
        return ResponseEntity.ok(new IqTestAnswerDTO(answer.getId(), answer.getQuestionId(), answer.getAnswerText(), answer.isCorrect(), answer.getImageUrl(), answer.getCreatedAt(), answer.getUpdatedAt()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Update an existing answer", description = "Update an existing IQ Test Answer based on id")
    public ResponseEntity<IqTestAnswerDTO> updateAnswer(@PathVariable("id") @Parameter(description = "ID of the answer to be updated") ObjectId id, @RequestBody IqTestAnswerDTO answerDTO) {
        IqTestAnswer answer = iqTestAnswerService.updateAnswer(id, answerDTO);
        return ResponseEntity.ok(new IqTestAnswerDTO(answer.getId(), answer.getQuestionId(), answer.getAnswerText(), answer.isCorrect(), answer.getImageUrl(), answer.getCreatedAt(), answer.getUpdatedAt()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Delete an answer by ID", description = "Delete an IQ Test Answer using its ID")
    public ResponseEntity<Void> deleteAnswer(@PathVariable("id") @Parameter(description = "ID of the answer to be deleted") ObjectId id) {
        iqTestAnswerService.deleteAnswerById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/question/{questionId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Get answers by question ID", description = "Retrieve IQ Test Answers by the question ID")
    public ResponseEntity<List<IqTestAnswerDTO>> getAnswersByQuestionId(@PathVariable("questionId") @Parameter(description = "ID of the question for which answers are needed") ObjectId questionId) {
        List<IqTestAnswer> answers = iqTestAnswerService.getAnswersByQuestionId(questionId);
        List<IqTestAnswerDTO> answerDTOs = answers.stream().map(answer -> new IqTestAnswerDTO(answer.getId(),
                answer.getQuestionId(), answer.getAnswerText(), answer.isCorrect(), answer.getImageUrl(), answer.getCreatedAt(), answer.getUpdatedAt())).collect(Collectors.toList());
        return ResponseEntity.ok(answerDTOs);
    }
}