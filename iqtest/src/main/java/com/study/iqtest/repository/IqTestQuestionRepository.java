package com.study.iqtest.repository;

import com.study.iqtest.model.IqTestQuestion;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IqTestQuestionRepository extends MongoRepository<IqTestQuestion, ObjectId> {
    List<IqTestQuestion> findByTestSettingId(ObjectId testId);
    void deleteByTestSettingId(ObjectId testSettingId);
}
