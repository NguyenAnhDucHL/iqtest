package com.study.iqtest.mapper;

import com.study.iqtest.dto.IqTestResultDTO;
import com.study.iqtest.model.IqTestResult;
import com.study.iqtest.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface IqTestResultMapper {

    IqTestResultMapper INSTANCE = Mappers.getMapper(IqTestResultMapper.class);

    @Mappings({
            @Mapping(source = "iqTestResult.id", target = "id"),
            @Mapping(source = "iqTestResult.testId", target = "testId"),
            @Mapping(source = "iqTestResult.score", target = "score"),
            @Mapping(source = "iqTestResult.resultDate", target = "resultDate"),
            @Mapping(source = "iqTestResult.feedback", target = "feedback"),
            @Mapping(source = "user.id", target = "userId"),
            @Mapping(source = "user.name", target = "userName"),
            @Mapping(source = "user.email", target = "userEmail"),
            @Mapping(source = "user.phoneNumber", target = "userPhoneNumber")
    })
    IqTestResultDTO toDto(IqTestResult iqTestResult, User user);
}
