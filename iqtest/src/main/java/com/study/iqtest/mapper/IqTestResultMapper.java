package com.study.iqtest.mapper;

import com.study.iqtest.dto.IqTestResultDTO;
import com.study.iqtest.model.IqTestResult;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IqTestResultMapper {
    IqTestResultDTO toDto(IqTestResult iqTestResult);
    IqTestResult toModal(IqTestResultDTO iqTestResultDTO);
}
