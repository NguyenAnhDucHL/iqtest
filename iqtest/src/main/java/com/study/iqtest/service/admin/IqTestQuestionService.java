package com.study.iqtest.service.admin;

import com.study.iqtest.dto.IqTestQuestionDTO;
import com.study.iqtest.mapper.IqTestAnswerMapper;
import com.study.iqtest.mapper.IqTestQuestionMapper;
import com.study.iqtest.model.IqTestAnswer;
import com.study.iqtest.model.IqTestQuestion;
import com.study.iqtest.repository.IqTestAnswerRepository;
import com.study.iqtest.repository.IqTestQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IqTestQuestionService {

    @Autowired
    private IqTestQuestionRepository iqTestQuestionRepository;

    @Autowired
    private IqTestAnswerRepository iqTestAnswerRepository;

    @Autowired
    private IqTestQuestionMapper iqTestQuestionMapper;

    @Autowired
    private IqTestAnswerMapper iqTestAnswerMapper;

    @Transactional
    public IqTestQuestionDTO createQuestion(IqTestQuestionDTO questionDTO) {
        IqTestQuestion question = iqTestQuestionMapper.toModal(questionDTO);
        question.setCreatedAt(LocalDateTime.now().toString());
        question.setUpdatedAt(LocalDateTime.now().toString());
        question = iqTestQuestionRepository.save(question);

        final String questionId = question.getId();
        List<IqTestAnswer> answers = questionDTO.getAnswers().stream()
                .map(iqTestAnswerMapper::toModal)
                .collect(Collectors.toList());
        answers.forEach(answer -> {
            answer.setQuestionId(questionId);
            answer.setCreatedAt(LocalDateTime.now().toString());
            answer.setUpdatedAt(LocalDateTime.now().toString());
        });
        iqTestAnswerRepository.saveAll(answers);

        questionDTO.setId(question.getId());
        questionDTO.setAnswers(answers.stream().map(iqTestAnswerMapper::toDto).collect(Collectors.toList()));
        questionDTO.setCreatedAt(question.getCreatedAt());
        questionDTO.setUpdatedAt(question.getUpdatedAt());

        return questionDTO;
    }

    @Transactional
    public IqTestQuestionDTO updateQuestion(String id, IqTestQuestionDTO questionDTO) {
        IqTestQuestion question = iqTestQuestionMapper.toModal(questionDTO);
        question.setId(id);
        question.setUpdatedAt(LocalDateTime.now().toString());
        question = iqTestQuestionRepository.save(question);
        iqTestAnswerRepository.deleteByQuestionId(id);

        final String questionId = question.getId();
        List<IqTestAnswer> answers = questionDTO.getAnswers().stream()
                .map(iqTestAnswerMapper::toModal)
                .collect(Collectors.toList());
        answers.forEach(answer -> {
            answer.setQuestionId(questionId);
            answer.setUpdatedAt(LocalDateTime.now().toString());
        });
        iqTestAnswerRepository.saveAll(answers);

        questionDTO.setId(question.getId());
        questionDTO.setAnswers(answers.stream().map(iqTestAnswerMapper::toDto).collect(Collectors.toList()));
        questionDTO.setCreatedAt(question.getCreatedAt());
        questionDTO.setUpdatedAt(question.getUpdatedAt());

        return questionDTO;
    }

    @Transactional
    public void deleteQuestion(String id) {
        iqTestQuestionRepository.deleteById(id);
        iqTestAnswerRepository.deleteByQuestionId(id);
    }

    public List<IqTestQuestionDTO> getQuestionsBySettingId(String settingId) {
        List<IqTestQuestion> questions = iqTestQuestionRepository.findByTestSettingId(settingId);
        return questions.stream().map(iqTestQuestion -> {
            List<IqTestAnswer> answers = iqTestAnswerRepository.findByQuestionId(iqTestQuestion.getId());
            IqTestQuestionDTO questionDTO = iqTestQuestionMapper.toDto(iqTestQuestion);
            questionDTO.setAnswers(answers.stream().map(iqTestAnswerMapper::toDto).collect(Collectors.toList()));
            return questionDTO;
        }).collect(Collectors.toList());
    }
}
