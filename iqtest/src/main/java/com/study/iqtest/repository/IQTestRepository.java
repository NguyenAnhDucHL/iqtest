package com.study.iqtest.repository;

import com.study.iqtest.model.IqTest;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IqTestRepository extends MongoRepository<IqTest, ObjectId> {
    List<IqTest> findByUserId(ObjectId userId);
}
