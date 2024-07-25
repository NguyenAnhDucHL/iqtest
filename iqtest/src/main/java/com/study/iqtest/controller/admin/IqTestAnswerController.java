package com.study.iqtest.controller.admin;
import com.study.iqtest.model.IqTestAnswer;
import com.study.iqtest.service.admin.IqTestAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RestController
@RequestMapping("/api/iqtest/answers")
@Api(value = "IQ Test Answers API", tags = {"IQ Test Answers"})
public class IqTestAnswerController {

    @Autowired
    private IqTestAnswerService iqTestAnswerService;

    @PostMapping
    @ApiOperation(value = "Create a new answer", response = IqTestAnswer.class)
    public ResponseEntity<IqTestAnswer> createAnswer(@RequestBody IqTestAnswer answer) {
        return ResponseEntity.ok(iqTestAnswerService.createAnswer(answer));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update an existing answer", response = IqTestAnswer.class)
    public ResponseEntity<IqTestAnswer> updateAnswer(@PathVariable String id, @RequestBody IqTestAnswer answer) {
        return ResponseEntity.ok(iqTestAnswerService.updateAnswer(id, answer));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete an answer by ID")
    public ResponseEntity<Void> deleteAnswer(@PathVariable String id) {
        iqTestAnswerService.deleteAnswerById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/question/{questionId}")
    @ApiOperation(value = "Get answers by question ID", response = List.class)
    public ResponseEntity<List<IqTestAnswer>> getAnswersByQuestionId(@PathVariable String questionId) {
        List<IqTestAnswer> answers = iqTestAnswerService.getAnswersByQuestionId(questionId);
        if (answers != null && !answers.isEmpty()) {
            return ResponseEntity.ok(answers);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

}
