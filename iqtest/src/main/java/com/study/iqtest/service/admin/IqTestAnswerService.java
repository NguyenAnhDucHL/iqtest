package com.study.iqtest.service.admin;

import com.study.iqtest.model.IqTestAnswer;
import com.study.iqtest.repository.IqTestAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IqTestAnswerService {

    @Autowired
    private IqTestAnswerRepository iqTestAnswerRepository;

    public IqTestAnswer createAnswer(IqTestAnswer answer) {
        return iqTestAnswerRepository.save(answer);
    }

    public IqTestAnswer updateAnswer(String id, IqTestAnswer answer) {
        answer.setId(id);
        return iqTestAnswerRepository.save(answer);
    }

    public void deleteAnswerById(String id) {
        iqTestAnswerRepository.deleteById(id);
    }

    public void deleteAnswerByQuestionId(String questionId) {
        iqTestAnswerRepository.deleteByQuestionId(questionId);
    }

    public List<IqTestAnswer> getAnswersByQuestionId(String questionId) {
        return iqTestAnswerRepository.findByQuestionId(questionId);
    }

    public void deleteAnswersByQuestionIds(List<String> questionIds) {
        iqTestAnswerRepository.deleteByQuestionIdIn(questionIds);
    }
}
