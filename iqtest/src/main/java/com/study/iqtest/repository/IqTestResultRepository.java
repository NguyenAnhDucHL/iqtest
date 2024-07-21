package com.study.iqtest.repository;

import com.study.iqtest.model.IqTestResult;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IqTestResultRepository extends MongoRepository<IqTestResult, String> {
    Optional<IqTestResult> findByTestId(String testId);
}
