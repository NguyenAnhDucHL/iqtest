package com.study.iqtest.service.admin;

import com.study.iqtest.dto.IqTestAnswerDTO;
import com.study.iqtest.exception.ResourceNotFoundException;
import com.study.iqtest.model.IqTestAnswer;
import com.study.iqtest.repository.IqTestAnswerRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IqTestAnswerService {

    @Autowired
    private IqTestAnswerRepository iqTestAnswerRepository;

    public IqTestAnswer createAnswer(IqTestAnswerDTO answerDTO) {
        IqTestAnswer answer = new IqTestAnswer();
        answer.setAnswerText(answerDTO.getAnswerText());
        answer.setCorrect(answerDTO.isCorrect());
        answer.setImageUrl(answerDTO.getImageUrl());
        answer.setQuestionId(answerDTO.getQuestionId());
        return iqTestAnswerRepository.save(answer);
    }

    public IqTestAnswer updateAnswer(ObjectId id, IqTestAnswerDTO answerDTO) {
        IqTestAnswer answer = iqTestAnswerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Answer not found"));
        answer.setAnswerText(answerDTO.getAnswerText());
        answer.setCorrect(answerDTO.isCorrect());
        answer.setImageUrl(answerDTO.getImageUrl());
        answer.setQuestionId(answerDTO.getQuestionId());
        return iqTestAnswerRepository.save(answer);
    }

    public List<IqTestAnswer> getAnswersByQuestionId(ObjectId questionId) {
        return iqTestAnswerRepository.findByQuestionId(questionId);
    }

    public void deleteAnswerByQuestionId(ObjectId questionId) {
        iqTestAnswerRepository.deleteByQuestionId(questionId);
    }

    public void deleteAnswerById(ObjectId id) {
        iqTestAnswerRepository.deleteById(id);
    }

    public void deleteAnswersByQuestionIds(List<ObjectId> questionIds) {
        iqTestAnswerRepository.deleteByQuestionIdIn(questionIds);
    }

}
