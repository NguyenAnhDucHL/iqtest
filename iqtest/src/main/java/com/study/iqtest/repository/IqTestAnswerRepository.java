package com.study.iqtest.repository;

import com.study.iqtest.model.IqTestAnswer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IqTestAnswerRepository extends MongoRepository<IqTestAnswer, String> {
    List<IqTestAnswer> findByQuestionId(String questionId);
    void deleteByQuestionId(String questionId);
    void deleteByQuestionIdIn(List<String> questionIds);
}

