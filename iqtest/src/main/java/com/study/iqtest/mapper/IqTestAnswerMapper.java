package com.study.iqtest.mapper;

import com.study.iqtest.dto.IqTestAnswerDTO;
import com.study.iqtest.model.IqTestAnswer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IqTestAnswerMapper {
    IqTestAnswerDTO toDto(IqTestAnswer iqTestAnswer);
    IqTestAnswer toModal(IqTestAnswerDTO iqTestAnswerDTO);
}