package com.study.iqtest.controller;

import com.study.iqtest.dto.IqTestAnswerDTO;
import com.study.iqtest.dto.UserDTO;
import com.study.iqtest.service.student.IqTestService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/api/iqtest")
@Tag(name = "IQ Test", description = "API for IQ Test operations such as starting, answering, and finishing tests")
public class IqTestController {

    @Autowired
    private IqTestService iqTestService;

    @PostMapping("/start")
    @Operation(summary = "Start a new IQ test", description = "Starts a new session of IQ test for a user")
    public ResponseEntity<?> startIQTest(@RequestBody UserDTO user) {
        return ResponseEntity.ok(iqTestService.startTest(user));
    }

    @GetMapping("/{testId}/questions")
    @Operation(summary = "Get questions for a specific IQ test", description = "Retrieves questions for a specific IQ test")
    public ResponseEntity<?> getIQTestQuestions(@PathVariable("testId") @Parameter(description = "ID of the IQ test") String testId) {
        return ResponseEntity.ok(iqTestService.getQuestions(testId));
    }

    @PostMapping("/{testId}/answer")
    @Operation(summary = "Submit an answer for a specific IQ test", description = "Submits an answer for a specific question in an IQ test")
    public ResponseEntity<?> submitIQTestAnswer(@PathVariable("testId") @Parameter(description = "ID of the IQ test") String testId, @RequestBody IqTestAnswerDTO answer) {
        return ResponseEntity.ok(iqTestService.submitAnswer(testId, answer));
    }

    @PostMapping("/{testId}/finish")
    @Operation(summary = "Finish a specific IQ test", description = "Finalizes the IQ test session and processes the results")
    public ResponseEntity<?> finishIQTest(@PathVariable("testId") @Parameter(description = "ID of the IQ test") String testId) {
        return ResponseEntity.ok(iqTestService.finishTest(testId));
    }

    @GetMapping("/{testId}/result")
    @Operation(summary = "Get the result of a specific IQ test", description = "Retrieves the result of a completed IQ test")
    public ResponseEntity<?> getIQTestResult(@PathVariable("testId") @Parameter(description = "ID of the IQ test") String testId) {
        return ResponseEntity.ok(iqTestService.getResult(testId));
    }

}

