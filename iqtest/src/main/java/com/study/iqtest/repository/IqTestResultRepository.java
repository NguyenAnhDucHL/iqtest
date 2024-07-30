package com.study.iqtest.repository;

import com.study.iqtest.model.IqTestResult;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IqTestResultRepository extends MongoRepository<IqTestResult, ObjectId> {
    Optional<IqTestResult> findByTestId(ObjectId testId);

    @Query(value = "{ 'testId': { $in: ?0 } }")
    Page<IqTestResult> findByTestIdIn(List<ObjectId> testIds, Pageable pageable);

    @Query(value = "{ 'testId': { $in: ?0 } }")
    List<IqTestResult> findByTestIdIn(List<ObjectId> testIds);
}
