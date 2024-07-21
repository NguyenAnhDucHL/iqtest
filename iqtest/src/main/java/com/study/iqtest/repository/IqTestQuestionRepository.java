package com.study.iqtest.repository;

import com.study.iqtest.model.IqTestQuestion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IqTestQuestionRepository extends MongoRepository<IqTestQuestion, String> {
    List<IqTestQuestion> findByTestSettingId(String testId);
}
