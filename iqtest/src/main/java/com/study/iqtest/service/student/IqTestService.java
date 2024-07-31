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
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
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
    private IqTestSettingRepository iqTestSettingRepository;

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
        List<IqTestSetting> settings = iqTestSettingRepository.findAll();
        if (settings.isEmpty()) {
            throw new IqTestException("No test settings available");
        }
        Random random = new Random();
        IqTestSetting randomSetting = settings.get(random.nextInt(settings.size()));

        IqTest iqTest = new IqTest();
        iqTest.setUserId(user.getId());
        iqTest.setTestDate(new Date());
        iqTest.setStatus("In Progress");
        iqTest.setTestSettingId(randomSetting.getId()); // Set the randomly selected test setting ID
        iqTest = iqTestRepository.save(iqTest);

        return iqTestMapper.toDto(iqTest);
    }


    public List<IqTestQuestionDTO> getQuestions(ObjectId testSettingId) {
        List<IqTestQuestion> questions = iqTestQuestionRepository.findByTestSettingId(testSettingId);

        return questions.stream().map(question -> {
            IqTestQuestionDTO questionDTO = iqTestQuestionMapper.toDto(question);
            List<IqTestAnswer> answers = iqTestAnswerRepository.findByQuestionId(question.getId());
            List<IqTestAnswerDTO> answerDTOs = answers.stream()
                    .map(iqTestAnswerMapper::toDto).peek(answer -> answer.setCorrect(false))
                    .collect(Collectors.toList());
            questionDTO.setAnswers(answerDTOs);
            return questionDTO;
        }).collect(Collectors.toList());
    }

    @Transactional
    public IqTestResultDTO finishTest(ObjectId testId, List<IqTestAnswerDTO> answers) {
        IqTest iqTest = iqTestRepository.findById(testId).orElseThrow(() -> new IqTestException("Test not found"));

        int score = 0;

        for (IqTestAnswerDTO answerDTO : answers) {
            if (answerDTO.isCorrect()) {
                score += 10; // Assuming each correct answer gives 10 points
            }
        }

        iqTest.setStatus("Finished");
        iqTestRepository.save(iqTest);

        IqTestResult result = new IqTestResult();
        result.setTestId(testId);
        result.setScore(score);
        result.setResultDate(new Date());
        result.setFeedback(generateFeedback(score));
        result = iqTestResultRepository.save(result);

        User user = userRepository.findById(iqTest.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        return iqTestResultMapper.toDto(result, user);
    }

    private String generateFeedback(int score) {
        if (score < 50) {
            return "Bad";
        } else if (score < 100) {
            return "Quite good";
        } else if (score < 150) {
            return "Good";
        } else if (score < 200) {
            return "Very good";
        } else {
            return "Excellent";
        }
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
}