package com.study.iqtest.repository;

import com.study.iqtest.model.IqTestAnswer;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IqTestAnswerRepository extends MongoRepository<IqTestAnswer, ObjectId> {
    List<IqTestAnswer> findByQuestionId(ObjectId questionId);
    void deleteByQuestionId(ObjectId questionId);
    void deleteByQuestionIdIn(List<ObjectId> questionIds);
}

