package com.study.iqtest.service;

import com.study.iqtest.dto.IqTestAnswerDTO;
import com.study.iqtest.dto.IqTestQuestionDTO;
import com.study.iqtest.dto.IqTestSettingDTO;
import com.study.iqtest.dto.UserDTO;
import com.study.iqtest.dto.IqTestResultDTO;
import com.study.iqtest.exception.IqTestException;
import com.study.iqtest.mapper.IqTestAnswerMapper;
import com.study.iqtest.mapper.IqTestMapper;
import com.study.iqtest.mapper.IqTestQuestionMapper;
import com.study.iqtest.mapper.IqTestResultMapper;
import com.study.iqtest.model.IqTest;
import com.study.iqtest.model.IqTestAnswer;
import com.study.iqtest.model.IqTestQuestion;
import com.study.iqtest.model.IqTestResult;
import com.study.iqtest.repository.IqTestAnswerRepository;
import com.study.iqtest.repository.IqTestQuestionRepository;
import com.study.iqtest.repository.IqTestRepository;
import com.study.iqtest.repository.IqTestResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    private IqTestMapper iqTestMapper;

    @Autowired
    private IqTestQuestionMapper iqTestQuestionMapper;

    @Autowired
    private IqTestAnswerMapper iqTestAnswerMapper;

    @Autowired
    private IqTestResultMapper iqTestResultMapper;

    public IqTestSettingDTO startTest(UserDTO user) {
        IqTest iqTest = new IqTest();
        iqTest.setUserId(user.getId());
        iqTest.setTestDate(LocalDateTime.now().toString());
        iqTest.setStatus("In Progress");
        iqTest = iqTestRepository.save(iqTest);
        return iqTestMapper.toDto(iqTest);
    }

    public List<IqTestQuestionDTO> getQuestions(String testId) {
        List<IqTestQuestion> questions = iqTestQuestionRepository.findByTestSettingId(testId);
        return questions.stream().map(iqTestQuestionMapper::toDto).collect(Collectors.toList());
    }

    public IqTestAnswerDTO submitAnswer(String testId, IqTestAnswerDTO answerDTO) {
        IqTestAnswer answer = iqTestAnswerMapper.toModal(answerDTO);
        answer.setCreatedAt(LocalDateTime.now().toString());
        answer = iqTestAnswerRepository.save(answer);
        return iqTestAnswerMapper.toDto(answer);
    }

    public IqTestResultDTO finishTest(String testId) {
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
        iqTestResult.setResultDate(LocalDateTime.now().toString());
        iqTestResult.setFeedback("Great Job!");
        iqTestResult = iqTestResultRepository.save(iqTestResult);

        return iqTestResultMapper.toDto(iqTestResult);
    }

    public IqTestResultDTO getResult(String testId) {
        Optional<IqTestResult> result = iqTestResultRepository.findByTestId(testId);
        if (result.isPresent()) {
            return iqTestResultMapper.toDto(result.get());
        } else {
            throw new IqTestException("Result not found");
        }
    }

    private int calculateScore(String testId) {
        List<IqTestAnswer> answers = iqTestAnswerRepository.findByQuestionId(testId);
        int score = 0;
        for (IqTestAnswer answer : answers) {
            if (answer.isCorrect()) {
                score += 10; // giả sử mỗi câu đúng được 10 điểm
            }
        }
        return score;
    }
}