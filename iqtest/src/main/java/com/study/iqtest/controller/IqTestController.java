package com.study.iqtest.controller;

import com.study.iqtest.dto.IqTestAnswerDTO;
import com.study.iqtest.dto.UserDTO;
import com.study.iqtest.service.IqTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/iqtest")
public class IqTestController {

    @Autowired
    private IqTestService iqTestService;

    @PostMapping("/start")
    public ResponseEntity<?> startIQTest(@RequestBody UserDTO user) {
        return ResponseEntity.ok(iqTestService.startTest(user));
    }

    @GetMapping("/{testId}/questions")
    public ResponseEntity<?> getIQTestQuestions(@PathVariable String testId) {
        return ResponseEntity.ok(iqTestService.getQuestions(testId));
    }

    @PostMapping("/{testId}/answer")
    public ResponseEntity<?> submitIQTestAnswer(@PathVariable String testId, @RequestBody IqTestAnswerDTO answer) {
        return ResponseEntity.ok(iqTestService.submitAnswer(testId, answer));
    }

    @PostMapping("/{testId}/finish")
    public ResponseEntity<?> finishIQTest(@PathVariable String testId) {
        return ResponseEntity.ok(iqTestService.finishTest(testId));
    }

    @GetMapping("/{testId}/result")
    public ResponseEntity<?> getIQTestResult(@PathVariable String testId) {
        return ResponseEntity.ok(iqTestService.getResult(testId));
    }

}
