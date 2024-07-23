package com.study.iqtest.repository;

import com.study.iqtest.model.IqTestSetting;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IqTestSettingRepository extends MongoRepository<IqTestSetting, String> {
}
