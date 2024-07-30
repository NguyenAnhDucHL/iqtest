package com.study.iqtest.service.admin;

import com.study.iqtest.dto.IqTestQuestionDTO;
import com.study.iqtest.mapper.IqTestAnswerMapper;
import com.study.iqtest.mapper.IqTestQuestionMapper;
import com.study.iqtest.model.IqTestAnswer;
import com.study.iqtest.model.IqTestQuestion;
import com.study.iqtest.repository.IqTestAnswerRepository;
import com.study.iqtest.repository.IqTestQuestionRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IqTestQuestionService {

    @Autowired
    private IqTestQuestionRepository iqTestQuestionRepository;

    @Autowired
    private IqTestAnswerRepository iqTestAnswerRepository;

    @Autowired
    private IqTestAnswerService iqTestAnswerService;

    @Autowired
    private IqTestQuestionMapper iqTestQuestionMapper;

    @Autowired
    private IqTestAnswerMapper iqTestAnswerMapper;

    @Transactional
    public IqTestQuestionDTO createQuestion(IqTestQuestionDTO questionDTO) {
        IqTestQuestion question = iqTestQuestionMapper.toModal(questionDTO);
        question.setCreatedAt(new Date());
        question.setUpdatedAt(new Date());
        question = iqTestQuestionRepository.save(question);

        final ObjectId questionId = question.getId();
        List<IqTestAnswer> answers = questionDTO.getAnswers().stream()
                .map(iqTestAnswerMapper::toModal)
                .collect(Collectors.toList());
        answers.forEach(answer -> {
            answer.setQuestionId(questionId);
            answer.setCreatedAt(new Date());
            answer.setUpdatedAt(new Date());
        });
        iqTestAnswerRepository.saveAll(answers);

        questionDTO.setId(question.getId());
        questionDTO.setAnswers(answers.stream().map(iqTestAnswerMapper::toDto).collect(Collectors.toList()));
        questionDTO.setCreatedAt(question.getCreatedAt());
        questionDTO.setUpdatedAt(question.getUpdatedAt());

        return questionDTO;
    }

    @Transactional
    public IqTestQuestionDTO updateQuestion(ObjectId id, IqTestQuestionDTO questionDTO) {
        IqTestQuestion question = iqTestQuestionMapper.toModal(questionDTO);
        question.setId(id);
        question.setUpdatedAt(new Date());
        question = iqTestQuestionRepository.save(question);
        iqTestAnswerRepository.deleteByQuestionId(id);

        final ObjectId questionId = question.getId();
        List<IqTestAnswer> answers = questionDTO.getAnswers().stream()
                .map(iqTestAnswerMapper::toModal)
                .collect(Collectors.toList());
        answers.forEach(answer -> {
            answer.setQuestionId(questionId);
            answer.setUpdatedAt(new Date());
        });
        iqTestAnswerRepository.saveAll(answers);

        questionDTO.setId(question.getId());
        questionDTO.setAnswers(answers.stream().map(iqTestAnswerMapper::toDto).collect(Collectors.toList()));
        questionDTO.setCreatedAt(question.getCreatedAt());
        questionDTO.setUpdatedAt(question.getUpdatedAt());

        return questionDTO;
    }

    @Transactional
    public void deleteQuestionByQuestionId(ObjectId questionId) {
        iqTestQuestionRepository.deleteById(questionId);
        iqTestAnswerService.deleteAnswerByQuestionId(questionId);
    }

    @Transactional
    public void deleteQuestionByTestSettingId(String testSettingId) {
        ObjectId testSettingObjectId = new ObjectId(testSettingId);
        List<IqTestQuestion> questions = iqTestQuestionRepository.findByTestSettingId(testSettingObjectId);
        List<ObjectId> questionIds = questions.stream()
                .map(IqTestQuestion::getId)
                .collect(Collectors.toList());

        iqTestQuestionRepository.deleteByTestSettingId(testSettingObjectId);
        iqTestAnswerService.deleteAnswersByQuestionIds(questionIds);
    }

    public List<IqTestQuestionDTO> getQuestionsBySettingId(ObjectId settingId) {
        List<IqTestQuestion> questions = iqTestQuestionRepository.findByTestSettingId(settingId);
        return questions.stream().map(iqTestQuestion -> {
            List<IqTestAnswer> answers = iqTestAnswerRepository.findByQuestionId(iqTestQuestion.getId());
            IqTestQuestionDTO questionDTO = iqTestQuestionMapper.toDto(iqTestQuestion);
            questionDTO.setAnswers(answers.stream().map(iqTestAnswerMapper::toDto).collect(Collectors.toList()));
            return questionDTO;
        }).collect(Collectors.toList());
    }
}