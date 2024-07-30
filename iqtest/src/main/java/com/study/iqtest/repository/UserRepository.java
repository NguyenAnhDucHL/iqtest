package com.study.iqtest.repository;

import com.study.iqtest.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {
    Optional<User> findByEmail(String email);

    @Query("{ $or: [ {'name': { $regex: ?0, $options: 'i' }}, {'email': { $regex: ?0, $options: 'i' }}, {'phoneNumber': { $regex: ?0, $options: 'i' }} ] }")
    List<User> findBySearchText(String searchText);
}
