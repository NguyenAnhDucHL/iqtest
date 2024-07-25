package com.study.iqtest.controller;

import com.study.iqtest.dto.IqTestAnswerDTO;
import com.study.iqtest.dto.UserDTO;
import com.study.iqtest.service.student.IqTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/iqtest")
@Api(value = "IQ Test API", tags = {"IQ Test"})
public class IqTestController {

    @Autowired
    private IqTestService iqTestService;

    @PostMapping("/start")
    @ApiOperation(value = "Start a new IQ test", response = ResponseEntity.class)
    public ResponseEntity<?> startIQTest(@RequestBody UserDTO user) {
        return ResponseEntity.ok(iqTestService.startTest(user));
    }

    @GetMapping("/{testId}/questions")
    @ApiOperation(value = "Get questions for a specific IQ test", response = ResponseEntity.class)
    public ResponseEntity<?> getIQTestQuestions(@PathVariable String testId) {
        return ResponseEntity.ok(iqTestService.getQuestions(testId));
    }

    @PostMapping("/{testId}/answer")
    @ApiOperation(value = "Submit an answer for a specific IQ test", response = ResponseEntity.class)
    public ResponseEntity<?> submitIQTestAnswer(@PathVariable String testId, @RequestBody IqTestAnswerDTO answer) {
        return ResponseEntity.ok(iqTestService.submitAnswer(testId, answer));
    }

    @PostMapping("/{testId}/finish")
    @ApiOperation(value = "Finish a specific IQ test", response = ResponseEntity.class)
    public ResponseEntity<?> finishIQTest(@PathVariable String testId) {
        return ResponseEntity.ok(iqTestService.finishTest(testId));
    }

    @GetMapping("/{testId}/result")
    @ApiOperation(value = "Get the result of a specific IQ test", response = ResponseEntity.class)
    public ResponseEntity<?> getIQTestResult(@PathVariable String testId) {
        return ResponseEntity.ok(iqTestService.getResult(testId));
    }

}
