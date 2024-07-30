package com.study.iqtest.service.student;

import com.study.iqtest.dto.*;
import com.study.iqtest.exception.IqTestException;
import com.study.iqtest.mapper.IqTestAnswerMapper;
import com.study.iqtest.mapper.IqTestMapper;
import com.study.iqtest.mapper.IqTestQuestionMapper;
import com.study.iqtest.mapper.IqTestResultMapper;
import com.study.iqtest.model.*;
import com.study.iqtest.repository.*;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IqTestService {

    @Autowired
    private IqTestRepository iqTestRepository;

    @Autowired
    private IqTestQuestionRepository iqTestQuestionRepository;

    @Autowired
    private IqTestAnswerRepository iqTestAnswerRepository;

    @Autowired
    private IqTestResultRepository iqTestResultRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IqTestMapper iqTestMapper;

    @Autowired
    private IqTestQuestionMapper iqTestQuestionMapper;

    @Autowired
    private IqTestAnswerMapper iqTestAnswerMapper;

    @Autowired
    private IqTestResultMapper iqTestResultMapper;

    public IqTestDTO startTest(UserDTO user) {
        IqTest iqTest = new IqTest();
        iqTest.setUserId(user.getId());
        iqTest.setTestDate(new Date());
        iqTest.setStatus("In Progress");
        iqTest = iqTestRepository.save(iqTest);
        return iqTestMapper.toDto(iqTest);
    }

    public List<IqTestQuestionDTO> getQuestions(ObjectId testId) {
        List<IqTestQuestion> questions = iqTestQuestionRepository.findByTestSettingId(testId);
        return questions.stream().map(iqTestQuestionMapper::toDto).collect(Collectors.toList());
    }

    public IqTestAnswerDTO submitAnswer(ObjectId testId, IqTestAnswerDTO answerDTO) {
        IqTestAnswer answer = iqTestAnswerMapper.toModal(answerDTO);
        answer.setCreatedAt(new Date());
        answer = iqTestAnswerRepository.save(answer);
        return iqTestAnswerMapper.toDto(answer);
    }

    public IqTestResultDTO finishTest(ObjectId testId) {
        Optional<IqTest> optionalTest = iqTestRepository.findById(testId);
        if (!optionalTest.isPresent()) {
            throw new IqTestException("Test not found");
        }
        IqTest iqTest = optionalTest.get();
        iqTest.setStatus("Finished");
        iqTest = iqTestRepository.save(iqTest);

        IqTestResult iqTestResult = new IqTestResult();
        iqTestResult.setTestId(testId);
        iqTestResult.setScore(calculateScore(testId));
        iqTestResult.setResultDate(new Date());
        iqTestResult.setFeedback("Great Job!");
        iqTestResult = iqTestResultRepository.save(iqTestResult);

        User user = userRepository.findById(iqTest.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));

        return iqTestResultMapper.toDto(iqTestResult, user);
    }

    public IqTestResultDTO getResult(ObjectId testId) {
        Optional<IqTestResult> result = iqTestResultRepository.findByTestId(testId);
        if (result.isPresent()) {
            IqTest iqTest = iqTestRepository.findById(result.get().getTestId()).orElseThrow(() -> new RuntimeException("Test not found"));
            User user = userRepository.findById(iqTest.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
            return iqTestResultMapper.toDto(result.get(), user);
        } else {
            throw new IqTestException("Result not found");
        }
    }

    private int calculateScore(ObjectId testId) {
        List<IqTestAnswer> answers = iqTestAnswerRepository.findByQuestionId(testId);
        int score = 0;
        for (IqTestAnswer answer : answers) {
            if (answer.isCorrect()) {
                score += 10;
            }
        }
        return score;
    }
}