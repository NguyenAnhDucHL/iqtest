package com.study.iqtest.controller.admin;
import com.study.iqtest.model.IqTestAnswer;
import com.study.iqtest.service.admin.IqTestAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/iqtest/answers")
public class IqTestAnswerController {

    @Autowired
    private IqTestAnswerService iqTestAnswerService;

    @PostMapping
    public ResponseEntity<IqTestAnswer> createAnswer(@RequestBody IqTestAnswer answer) {
        return ResponseEntity.ok(iqTestAnswerService.createAnswer(answer));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IqTestAnswer> updateAnswer(@PathVariable String id, @RequestBody IqTestAnswer answer) {
        return ResponseEntity.ok(iqTestAnswerService.updateAnswer(id, answer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnswer(@PathVariable String id) {
        iqTestAnswerService.deleteAnswerById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<List<IqTestAnswer>> getAnswersByQuestionId(@PathVariable String questionId) {
        return ResponseEntity.ok(iqTestAnswerService.getAnswersByQuestionId(questionId));
    }

}
