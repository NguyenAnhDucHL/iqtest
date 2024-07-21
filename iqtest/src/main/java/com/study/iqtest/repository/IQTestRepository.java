package com.study.iqtest.repository;

import com.study.iqtest.model.IqTest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IqTestRepository extends MongoRepository<IqTest, String> {
    List<IqTest> findByUserId(String userId);
}
