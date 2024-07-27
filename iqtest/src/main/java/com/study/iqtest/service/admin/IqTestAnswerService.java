package com.study.iqtest.service.admin;

import com.study.iqtest.dto.IqTestAnswerDTO;
import com.study.iqtest.exception.ResourceNotFoundException;
import com.study.iqtest.model.IqTestAnswer;
import com.study.iqtest.repository.IqTestAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IqTestAnswerService {

    @Autowired
    private IqTestAnswerRepository iqTestAnswerRepository;

    public IqTestAnswer createAnswer(IqTestAnswerDTO answerDTO) {
        IqTestAnswer answer = new IqTestAnswer(); // Create a new answer entity from DTO
        answer.setAnswerText(answerDTO.getAnswerText());
        answer.setCorrect(answerDTO.isCorrect());
        answer.setImageUrl(answerDTO.getImageUrl());
        answer.setQuestionId(answerDTO.getQuestionId());
        return iqTestAnswerRepository.save(answer);
    }

    public IqTestAnswer updateAnswer(String id, IqTestAnswerDTO answerDTO) {
        IqTestAnswer answer = iqTestAnswerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Answer not found"));
        answer.setAnswerText(answerDTO.getAnswerText());
        answer.setCorrect(answerDTO.isCorrect());
        answer.setImageUrl(answerDTO.getImageUrl());
        answer.setQuestionId(answerDTO.getQuestionId());
        return iqTestAnswerRepository.save(answer);
    }

    public List<IqTestAnswer> getAnswersByQuestionId(String questionId) {
        return iqTestAnswerRepository.findByQuestionId(questionId);
    }

    public void deleteAnswerByQuestionId(String questionId) {
        iqTestAnswerRepository.deleteByQuestionId(questionId);
    }

    public void deleteAnswerById(String id) {
        iqTestAnswerRepository.deleteById(id);
    }

    public void deleteAnswersByQuestionIds(List<String> questionIds) {
        iqTestAnswerRepository.deleteByQuestionIdIn(questionIds);
    }
}