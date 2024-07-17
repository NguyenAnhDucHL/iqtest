package com.study.iqtest.repository;

import com.study.iqtest.model.IqTest;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface IQTestRepository extends MongoRepository<IqTest, String> {
    List<IqTest> findByUserId(String userId);
}
